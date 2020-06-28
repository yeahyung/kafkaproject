package com.example.kafkaproject.controller;

import com.example.kafkaproject.service.KafkaConsume;
import com.example.kafkaproject.service.KafkaProduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class mainController {

    @Autowired
    KafkaConsume kafkaConsumer;

    @Autowired
    KafkaProduce kafkaProducer;

    @RequestMapping(value="/producer", method= RequestMethod.GET)
    @ResponseBody
    public String producer() throws Exception{
        return kafkaProducer.produce();
    }

    @RequestMapping(value="/consumer", method= RequestMethod.GET)
    public void consumer() throws Exception{
        kafkaConsumer.consume();
    }
}
