package com.example.kafkaproject.config;

import com.example.kafkaproject.vo.User;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@EnableKafka
public class ConsumerConfiguration {

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        // server host 및 port 지정
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "manager:9092");

        // key serialize 지정
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // group id 지정
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "yeagroup");

        // offset 지정
        // earliest -> 처음부터, latest -> 가장 최신의 offset부터, none -> offset 정보가 없다면 exception 발생시킨다.
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        // @Todo 쓰레드 몇개 쓸건 지로 기억, 다시 확인
        factory.setConcurrency(3);
        return factory;
    }



    public Map<String, Object> consumerUserConfigs() {
        Map<String, Object> props = new HashMap<>();

        // server host 및 port 지정
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "manager:9092");

        // key serialize 지정
        //props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        //props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // group id 지정
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "yeagroup");

        // offset 지정
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return props;
    }

    public ConsumerFactory<String, User> consumerUserFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerUserConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerUserContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerUserFactory());
        // @Todo 쓰레드 몇개 쓸건 지로 기억, 다시 확인
        factory.setConcurrency(3);
        return factory;
    }
}
