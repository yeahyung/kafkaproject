package com.example.kafkaproject.config;

import com.example.kafkaproject.vo.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableKafka
public class ProducerConfiguration {

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();

        // server host 및 port 지정
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "manager:9092");

        // key serialize 지정
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // StringSerializer 대신 JsonSerializer 사용해야 value 에 string 말고 DTO 형태로 전달 가능
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    // value serializer 변경하면 여기서도 오른쪽 String 변경
    // 참고 https://github.com/sieunkr/spring-kafka/
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        // Bean을 통하여 의존성 주입
        return new KafkaTemplate<String, String>(producerFactory());
    }


    public Map<String, Object> producerUserConfigs() {
        Map<String, Object> props = new HashMap<>();

        // server host 및 port 지정
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "manager:9092");

        // key serialize 지정
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // JsonSerializer
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

    public ProducerFactory<String, User> producerCustomFactory() {
        return new DefaultKafkaProducerFactory<>(producerUserConfigs());
    }

    @Bean
    public KafkaTemplate<String, User> kafkaUserTemplate() {
        // Bean을 통하여 의존성 주입
        return new KafkaTemplate<String, User>(producerCustomFactory());
    }
}
