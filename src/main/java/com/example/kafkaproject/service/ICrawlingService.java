package com.example.kafkaproject.service;

import org.jsoup.select.Elements;

public interface ICrawlingService {
    // https://finance.naver.com/sise/lastsearch2.nhn 크롤링
    Elements getCrawlingNAVERResponse() throws Exception;

    // 크롤링 한 결과를 파싱하여 ES 에 적재할 수 있는 json 형태로 변환
    String convertElementToString(Elements element) throws Exception;

    // json 형태를 ES 에 bulk api 호출하여 전송
    Boolean exportCrawlingResponseToElasticsearch(String stockInfo);
}
