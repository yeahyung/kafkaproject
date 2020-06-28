package com.example.kafkaproject;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Properties;

@Controller
public class mainController {
    @RequestMapping(value="/producer", method= RequestMethod.GET)
    @ResponseBody
    public String producer() throws Exception{
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "manager:9092");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(configs);

        for(int i=0;i<10;i++){
            ProducerRecord record = new ProducerRecord<String, String>("testtest", Integer.toString(i));
            producer.send(record);
        }
        System.out.println("data complete");
        producer.close();

        return "OK";
    }

    @RequestMapping(value="/consumer", method= RequestMethod.GET)
    public void consumer() throws Exception{
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "manager:9092");
        configs.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        configs.put("group.id", "test_group");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(configs);

        consumer.subscribe(Arrays.asList("testtest"));

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(500); // 500ms 만큼 kafka에 데이터가 쌓일동안 기다리고, 가져온다.
            for(ConsumerRecord<String, String> record : records){
                System.out.println(record);
                System.out.println(record.value());
            }
        }
    }
}
