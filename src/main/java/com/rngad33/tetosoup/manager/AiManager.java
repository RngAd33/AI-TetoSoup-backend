package com.rngad33.tetosoup.manager;

import cn.hutool.core.collection.CollUtil;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * AI调用封装类，用于代码复用
 */
@Service
public class AiManager {

    /**
     * 调用客户端
     */
    @Resource
    private ArkService arkService;

    /**
     * 调用 AI（初始化版）
     *
     * @param systemPrompt 系统预设
     * @param userPrompt 用户预设
     * @return 返回结果
     */
    public String doChat(String systemPrompt, String userPrompt) {
        // 创建消息列表
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM)
                .content(systemPrompt)
                .build();
        final ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER).content(userPrompt)
                .build();
        messages.add(systemMessage);
        messages.add(userMessage);

        // 传递消息，单次调用
        return doInvoke(messages);
    }

    /**
     * 调用 AI（通用版）
     *
     * @param chatMessageList 消息列表
     * @return 返回结果
     */
    public String doChat(List<ChatMessage> chatMessageList) {
        // 传递消息，单次调用
        return doInvoke(chatMessageList);
    }

    /**
     * AI单次调用方法
     *
     * @param messages 消息列表
     * @return 返回结果
     */
    private String doInvoke(List<ChatMessage> messages) {
        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
                .botId("bot-20250407152509-khg8p")
                .messages(messages)
                .build();

        List<ChatCompletionChoice> choiceList = arkService.createBotChatCompletion(chatCompletionRequest)
                .getChoices();
        if (CollUtil.isEmpty(choiceList)) {
            throw new RuntimeException("————！！AI没有返回结果！！————");
        }
        String content = (String) choiceList.get(0)
                .getMessage()
                .getContent();
        System.out.println("返回结果：" + content);
        return content;
    }

}