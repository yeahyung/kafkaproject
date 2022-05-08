package com.example.kafkaproject.service.impl;

import com.example.kafkaproject.service.ICrawlingService;
import com.example.kafkaproject.util.HttpUtil;
import com.example.kafkaproject.util.JsonUtil;
import com.example.kafkaproject.vo.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@Service
public class CrawlingServiceImpl implements ICrawlingService {

    @Autowired
    HttpUtil httpUtil;

    @Value("${default.index.name}")
    private String defaultIndexName;

    private final int defaultArraySize = 12;

    @Override
    public Elements getCrawlingNAVERResponse() throws Exception {
        Document doc = Jsoup.connect("https://finance.naver.com/sise/lastsearch2.nhn").get();
        // https://finance.naver.com/sise/sise_quant.naver

        Elements elements = doc.getElementsByClass("type_5");
        return elements.select("tbody tr");
    }

    @Override
    public String convertElementToString(Elements element) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();

        for(String stockInfo : element.eachText()){
            // 주식 정보가 아닌 경우
            if(stockInfo.indexOf("%") == -1) {
                continue;
            }

            // 주식 이름에 띄어 쓰기가 있는 케이스가 있어 additionalArraySize 추가로 임시 조치..
            String[] arrays = stockInfo.split(" ");
            int additionalArraySize = arrays.length - defaultArraySize;
            String stockName = arrays[1];
            for(int i=1; i<additionalArraySize; i++) {
                stockName += " " + arrays[1 + i];
            }

            // 검색 순위, 종목명, 검색 비율, 현재가, 등락률(전날 대비), 거래량, 시가(시장 가격), 고가, 저가
            Date currentTime = new Date();
            Stock stock = Stock.builder()
                    .ratio(arrays[0])
                    .name(stockName)
                    .searchRatio(arrays[2 + additionalArraySize])
                    .currentValue(arrays[3 + additionalArraySize])
                    .compareToYesterday(arrays[5 + additionalArraySize])
                    .transactionVolume(arrays[6 + additionalArraySize])
                    .marketPrice(arrays[7 + additionalArraySize])
                    .highestPrice(arrays[8 + additionalArraySize])
                    .lowestPrice(arrays[9 + additionalArraySize])
                    .currentTime(currentTime)
                    .build();

            // index
            stringBuffer.append(createIndexInfo(arrays[1]).toLowerCase(Locale.ROOT) + "\r\n");
            // body
            stringBuffer.append(JsonUtil.convertMapToJsonLine(stock) + "\r\n");

            // index
            stringBuffer.append(createIndexInfo("stock") + "\r\n");
            // body
            stringBuffer.append(JsonUtil.convertMapToJsonLine(stock) + "\r\n");
        }
        System.out.println(stringBuffer);
        return stringBuffer.toString();
    }

    private String createIndexInfo(String index){
        HashMap<String, Object> indexMap = new HashMap<>();
        HashMap<String, Object> indexInfoMap = new HashMap<>();

        indexInfoMap.put("_index", defaultIndexName + index);
        indexMap.put("index", indexInfoMap);

        return JsonUtil.convertMapToJsonLine(indexMap);
    }

    @Override
    public Boolean exportCrawlingResponseToElasticsearch(String stockInfo) {
        try{
            httpUtil.putBulkToElasticsearch("http://myesdomain:9200/_bulk", stockInfo);
            System.out.println("success to send bulk to es");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
