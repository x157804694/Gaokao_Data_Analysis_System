package com.hust.gaokao_data_analysis_system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hust.gaokao_data_analysis_system.mapper")
public class GaokaoDataAnalysisSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaokaoDataAnalysisSystemApplication.class, args);
    }

}
