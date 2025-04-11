package com.rngad33.tetosoup.config;

import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * AI配置
 */
@Configuration
@ConfigurationProperties(prefix = "ai")
@Data
public class AiConfig {

    private String apiKey;

    /**
     * AI客户端初始化
     *
     * @return 客户端 Bean
     */
    @Bean
    public ArkService arkService() {
        ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
        Dispatcher dispatcher = new Dispatcher();
        return ArkService.builder()
                .dispatcher(dispatcher)
                .connectionPool(connectionPool).baseUrl("https://ark.cn-beijing.volces.com/api/v3/")   // 默认路径
                .apiKey(apiKey)
                .build();
    }
}