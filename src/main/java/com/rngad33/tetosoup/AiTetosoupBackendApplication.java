package com.rngad33.tetosoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动入口
 */
@SpringBootApplication
@MapperScan("com.rngad33.tetosoup.web.mapper")
public class AiTetosoupBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiTetosoupBackendApplication.class, args);
    }

}