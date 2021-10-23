package com.example.kafkaproject.scheduler;

import com.example.kafkaproject.service.Crawling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CrawlingScheduler {

    @Autowired
    Crawling crawling;

    // 초 분 시 일 월 요
    // 월~금 9시~18시 1분마다
    //@Scheduled(cron = "0 * 9-18 * * MON-FRI")
    // 1분마다 실행
    @Scheduled(cron = "0 * * * * *")
    public void crawlingScheduler() throws Exception {
        System.out.println(new Date());
        crawling.crawlingNaver();
    }
}
