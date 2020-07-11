package com.example.kafkaproject.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CrawlingScheduler {


    @Scheduled(cron = "0 0 3 * * *")
    public void crawlingScheduler(){
        ;
    }
}
