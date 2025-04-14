package com.rngad33.tetosoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动入口
 *
 * @url http://localhost:8080/api/doc.html 接口文档地址
 */
@SpringBootApplication
@MapperScan("com.rngad33.tetosoup.mapper")
public class AiTetoSoupApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiTetoSoupApplication.class, args);
    }

}