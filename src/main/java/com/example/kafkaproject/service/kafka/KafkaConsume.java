package com.example.kafkaproject.service.kafka;

import com.example.kafkaproject.vo.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaConsume {

    Logger logger = LoggerFactory.getLogger(KafkaConsume.class);

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


    // @Todo
    // @KafkaListener를 설정하면, 스프링 프로젝트가 시작되자 마자 Consuming 시작, 다른 API url은 작동하지 않는다.
    // Consuming만 하는 전용 project를 하나 따로 만들어서 써야하는 듯 싶다.
    //@KafkaListener(topics = "testtest")
    public void consume(String payload) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info(payload);
    }


    //@KafkaListener(topics = "testtest", containerFactory = "kafkaListenerUserContainerFactory")
    public void consumeDto(List<User> payload) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        payload.forEach(userDTO -> logger.info(userDTO.getMessage()));
        logger.info("Batch Message Size : " + payload.size());
    }
}
