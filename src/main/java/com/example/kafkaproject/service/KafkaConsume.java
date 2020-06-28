package com.example.kafkaproject.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Service
public class KafkaConsume {

    public void consume(){
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
