package com.rngad33.tetosoup.service.impl;

import com.rngad33.tetosoup.manager.AiManager;
import com.rngad33.tetosoup.service.ChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户端服务实现
 */
public class ChatServiceImpl implements ChatService {

    @Resource
    private AiManager aiManager;

    /**
     * 全局消息映射
     */
    final Map<Long, List<ChatMessage>> globalMessageMap = new HashMap<>();

    @Override
    public String doChat(long roomId, String message) {

        // 系统预设（仅添加一次）
        final String systemPrompt = "你是一位海龟汤游戏主持人，当我说“开始”的时候，你要给我出一道海龟汤游戏的“汤面”。然后我会依次问你一些问题，你只能回答“是”、“否”或者“与此无关”。但在以下几种情况下，你需要结束游戏，并输出游戏的“汤底”：\n" +
                "- 我给出“不想玩了、或者想要答案”之类的表达\n" +
                "- 我几乎已经讲明了真相，或者已经还原了故事，或者所有关键问题都已经询问过\n" +
                "- 我输入“退出”\n" +
                "- 经过10个问题后，我还是没有答到关键信息、或者完全没有头绪\n" +
                "\n" +
                "注意事项\n" +
                "汤面设计：谜题应当有趣且逻辑严密，可以从网上找热门海龟汤，答案需出人意料但合理。\n" +
                "回答限制：严格遵守“是”、“否”或“与此无关”的回答规则，不得提供额外提示。\n" +
                "结束时机：在符合结束条件时，及时揭示“汤底”，避免玩家陷入无效推理。\n" +
                "\n" +
                "示例\n" +
                "玩家输入：“开始”\n" +
                "AI 回复（汤面）：\n" +
                "“一个人走进餐厅，点了一碗海龟汤，喝了一口后突然冲出餐厅自杀了。为什么？”\n" +
                "玩家提问：“他是因为汤太难喝了吗？”\n" +
                "AI 回复：“否。”\n" +
                "玩家提问：“他认识餐厅里的人吗？”\n" +
                "AI 回复：“与此无关。”\n" +
                "玩家输入：“退出。”\n" +
                "AI 回复（汤底）：\n" +
                "“这个人曾和同伴在海上遇难，同伴死后，他靠吃同伴的尸体活了下来。餐厅的海龟汤让他意识到自己吃的其实是人肉，因此崩溃自杀。”\n";

        // 准备消息列表（需要关联历史上下文）
        final ChatMessage systemMessage = ChatMessage.builder()
                .role(ChatMessageRole.SYSTEM)
                .content(systemPrompt)   // 系统预设
                .build();
        final ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(message)   // 用户消息
                .build();
        List<ChatMessage> messages = new ArrayList<>();

        // 判断是否为首次对话
        if (message.equals("开始") && !globalMessageMap.containsKey(roomId)) {
            // - 首次对话，创建房间、初始化消息列表、添加系统预设
            globalMessageMap.put(roomId, messages);
            messages.add(systemMessage);   // 系统预设仅首次对话添加
        } else {
            // - 二次对话，读取过去的消息列表
            messages = globalMessageMap.get(roomId);
        }
        messages.add(userMessage);   // 读取用户预设

        // 调用AI
        String answer = aiManager.doChat(messages);
        final ChatMessage assistantMessage = ChatMessage.builder()
                .role(ChatMessageRole.ASSISTANT)
                .content(systemPrompt)
                .build();
        messages.add(assistantMessage);   // 消息列表追加

        // 返回信息
        return answer;
    }
}