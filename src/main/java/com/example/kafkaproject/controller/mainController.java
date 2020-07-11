package com.example.kafkaproject.controller;

import com.example.kafkaproject.service.Crawling;
import com.example.kafkaproject.service.KafkaConsume;
import com.example.kafkaproject.service.KafkaProduce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class mainController {

    Logger logger = LoggerFactory.getLogger(mainController.class);

    @Autowired
    KafkaConsume kafkaConsumer;

    @Autowired
    KafkaProduce kafkaProducer;

    @Autowired
    Crawling crawlingService;

    public mainController(Crawling crawlingService) {
        this.crawlingService = crawlingService;
    }

    @RequestMapping(value="/crawling", method = RequestMethod.GET)
    @ResponseBody
    public String crawling() throws IOException{
        crawlingService.crawlingNaver();
        return "OK";
    }

    @RequestMapping(value="/producer", method = RequestMethod.GET)
    @ResponseBody
    public String producer() throws Exception{
        logger.info("log");
        return kafkaProducer.producer();
    }

    @RequestMapping(value="/producerJson", method = RequestMethod.GET)
    @ResponseBody
    public String producerJson() throws Exception{
        return kafkaProducer.producerJson();
    }

    // 원할때만 consume 하려면 이런식으로?
    @RequestMapping(value="/consumer", method = RequestMethod.GET)
    public void consumer() throws Exception{
        kafkaConsumer.consume();
    }

    @RequestMapping(value="/consumeonetime", method = RequestMethod.GET)
    @ResponseBody
    public StringBuffer consumeOneTime(){
        //return kafkaConsumer.consumeOneTime();
        return null;
    }
}
