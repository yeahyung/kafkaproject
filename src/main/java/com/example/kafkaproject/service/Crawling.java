package com.example.kafkaproject.service;

import com.example.kafkaproject.util.HttpUtil;
import com.example.kafkaproject.util.JsonUtil;
import com.example.kafkaproject.vo.Stock;
import com.example.kafkaproject.vo.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class Crawling {

    @Value("#{${product.name}}")
    private Map<String, String> product;

    @Autowired
    KafkaTemplate<String, Stock> kafkaStockTemplate;

    @Autowired
    HttpUtil httpUtil;

    public void crawlingTest() throws IOException {
        Document doc = Jsoup.connect("https://finance.naver.com/sise/lastsearch2.nhn").get();

        //Elements elements = doc.select("table tbody tr");

        Elements elements = doc.getElementsByClass("type_5");

        Elements element = elements.select("tbody tr");
        for(String test : element.eachText()){
            //System.out.println(test);

            String[] arrays = test.split(" ");

            // 검색 순위, 종목명, 검색 비율, 현재가, 등락률(전날 대비), 거래량, 시가(시장 가격), 고가, 저가
            Stock stock = new Stock(new Date(), arrays[0], arrays[1], product.get(arrays[1]), arrays[2], arrays[3], arrays[5], arrays[6], arrays[7], arrays[8], arrays[9]);

            System.out.println(stock);
            //System.out.println(arrays[1] + " : " + arrays[3]);

            if(product.containsKey(arrays[1])){
                System.out.println(product.get(arrays[1]));
                //kafkaStockTemplate.send("testtesttt", stock);
            }
        }
    }

    public void crawlingNaver() throws Exception {
        Document doc = Jsoup.connect("https://finance.naver.com/sise/lastsearch2.nhn").get();

        //Elements elements = doc.select("table tbody tr");

        Elements elements = doc.getElementsByClass("type_5");

        Elements element = elements.select("tbody tr");
        StringBuffer stringBuffer = new StringBuffer();

        for(String test : element.eachText()){
            //System.out.println(test);

            String[] arrays = test.split(" ");

            // 검색 순위, 종목명, 검색 비율, 현재가, 등락률(전날 대비), 거래량, 시가(시장 가격), 고가, 저가
            Stock stock = new Stock(new Date(), arrays[0], arrays[1], product.get(arrays[1]), arrays[2], arrays[3], arrays[5], arrays[6], arrays[7], arrays[8], arrays[9]);

            //System.out.println(stock);
            //System.out.println(arrays[1] + " : " + arrays[3]);

            // index
            stringBuffer.append(createIndexInfo(arrays[1]).toLowerCase(Locale.ROOT) + "\r\n");
            // body
            stringBuffer.append(JsonUtil.convertMapToJsonLine(stock) + "\r\n");

            // index
            stringBuffer.append(createIndexInfo("stock") + "\r\n");
            // body
            stringBuffer.append(JsonUtil.convertMapToJsonLine(stock) + "\r\n");

        }
        sendBulkToElasticsearch(stringBuffer.toString());
    }

    private String createIndexInfo(String index){
        HashMap<String, Object> indexMap = new HashMap<>();
        HashMap<String, Object> indexInfoMap = new HashMap<>();

        indexInfoMap.put("_index", index);
        indexMap.put("index", indexInfoMap);

        return JsonUtil.convertMapToJsonLine(indexMap);
    }

    private void sendBulkToElasticsearch(String msg) throws Exception {
        String url = "http://myesdomain:9200/_bulk";

        httpUtil.putBulkToElasticsearch(url, msg);
    }
}
