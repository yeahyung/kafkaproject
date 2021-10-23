package com.example.kafkaproject.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HttpUtil {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(
            value = { Exception.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000)
    )

    public void putBulkToElasticsearch(String url, String message) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(message, headers);

        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    @Recover
    public void recover(Exception e, String url, String message){
        System.out.println("recover method");
    }

}
