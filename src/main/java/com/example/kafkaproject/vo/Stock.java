package com.example.kafkaproject.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock {
    Date currentTime;
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
