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

    private String direct;
    private String level1;
    private String level2;
    private String level3;
    private String level4;
    private String level5;

}
