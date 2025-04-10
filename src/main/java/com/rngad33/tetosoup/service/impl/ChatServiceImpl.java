package com.rngad33.tetosoup.service.impl;

import com.rngad33.tetosoup.manager.AiManager;
import com.rngad33.tetosoup.service.ChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceImpl implements ChatService {

    /**
     * 调用客户端
     */
    @Resource
    private AiManager aiManager;

    @Override
    public String doChat(String message) {

        // 准备系统预设
        final String systemPrompt = "";

        // 准备消息列表（关联历史上下文）
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM)
                .content(systemPrompt)
                .build();
        final ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER).content(message)
                .build();
        messages.add(systemMessage);
        messages.add(userMessage);

        // 调用AI
        String answer = aiManager.doChat(messages);

        // 返回信息
        return answer;
    }
}