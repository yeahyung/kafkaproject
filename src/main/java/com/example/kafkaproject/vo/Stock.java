package com.example.kafkaproject.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    String ratio;
    String name;
    String searchRatio;
    String currentValue;
    String compareToYesterday;
    String transactionVolume;
    String marketPrice;
    String highestPrice;
    String lowestPrice;
}
