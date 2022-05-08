package com.example.kafkaproject.service.kafka;

import com.example.kafkaproject.vo.User;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaProduce {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, User> kafkaUserTemplate;

    public String produce(){
        Properties configs = new Properties();
        configs.put("bootstrap.servers", "manager:9092");
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // StringSerializer 대신 JsonSerializer 사용해야 value 에 string 말고 DTO 형태로 전달 가능
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

    public String producer(){
        kafkaTemplate.send("testtest", "hello world");

        return "OK";
    }

    public String producerJson(){
        User user = new User();
        user.setUserId("yea");
        user.setMessage("This is custom message");

        kafkaUserTemplate.send("testtest", user);

        return "OK";
    }
}
