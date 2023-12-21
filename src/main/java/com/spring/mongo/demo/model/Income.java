package com.spring.mongo.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Income {

    private Double direct;
    private Double level1;
    private Double level2;
    private Double level3;
    private Double level4;
    private Double level5;



}
