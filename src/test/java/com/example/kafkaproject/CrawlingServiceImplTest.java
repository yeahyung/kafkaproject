package com.example.kafkaproject;

import com.example.kafkaproject.service.impl.CrawlingServiceImpl;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"local", "common"})
public class CrawlingServiceImplTest {

    @Autowired
    CrawlingServiceImpl crawling;

    @Test
    public void crawlingTest() {
        try{
            Elements elements = crawling.getCrawlingNAVERResponse();
            String stockInfo = crawling.convertElementToString(elements);
            //System.out.println(stockInfo);
            assertThat(crawling.exportCrawlingResponseToElasticsearch(stockInfo)).isTrue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
