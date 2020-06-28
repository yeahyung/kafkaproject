package com.example.kafkaproject.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaProduce {

    public String produce(){
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "manager:9092");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        org.apache.kafka.clients.producer.KafkaProducer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String,String>(configs);

        for(int i=0;i<10;i++){
            ProducerRecord record = new ProducerRecord<String, String>("testtest", Integer.toString(i));
            producer.send(record);
        }
        System.out.println("data complete");
        producer.close();

        return "OK";
    }
}
