package com.rngad33.tetosoup.service;

/**
 * 客户端服务接口
 */
public interface ChatService {

    /**
     * 与 AI 对话
     *
     * @param message 用户输入的信息
     * @return 返回结果
     */
    String doChat(long roomId, String message);

}