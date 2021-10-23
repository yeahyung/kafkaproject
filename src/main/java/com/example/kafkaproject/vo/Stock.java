package com.example.kafkaproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    Date currentTime;
    String ratio;
    String name;
    String nameInEng;
    String searchRatio;
    String currentValue;
    String compareToYesterday;
    String transactionVolume;
    String marketPrice;
    String highestPrice;
    String lowestPrice;
}
