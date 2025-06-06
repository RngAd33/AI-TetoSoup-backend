package com.rngad33.tetosoup.service.impl;

import com.rngad33.tetosoup.manager.AiManager;
import com.rngad33.tetosoup.model.ChatRoom;
import com.rngad33.tetosoup.service.ChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 客户端服务实现
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private AiManager aiManager;

    /**
     * 全局消息映射
     */
    final Map<Long, List<ChatMessage>> globalMessageMap = new HashMap<>();

    /**
     * 与 AI 对话
     *
     * @param roomId 房间号
     * @param message 用户输入的信息
     * @return 返回结果
     */
    @Override
    public String doChat(long roomId, String message) {

        // 系统预设（仅添加一次）
        final String systemPrompt = "你是一位海龟汤游戏主持人，当我说“开始”的时候，你要给我出一道海龟汤游戏的“汤面”。然后我会依次问你一些问题，你只能回答“是”、“否”或者“与此无关”。但在以下几种情况下，你需要结束游戏、给出提示词\"over\"并输出游戏的“汤底”：\n" +
                "- 我给出“不想玩了”、或者“想要答案”之类的表达\n" +
                "- 我几乎已经讲明了真相，或者已经还原了故事，或者所有关键问题都已经询问过\n" +
                "- 我输入“退出”或“结束”\n" +
                "- 经过10个问题后，我还是没有回答到关键信息、或者完全没有头绪\n" +
                "\n" +
                "注意事项\n" +
                "- 汤面设计：谜题应当有趣且逻辑严密，必须从网上找经典、热门海龟汤，答案需出人意料但合理。\n" +
                "- 回答限制：严格遵守“是”、“否”或“与此无关”的回答规则，不得提供额外提示。\n" +
                "- 结束时机：在符合结束条件时，及时揭示“汤底”，避免玩家陷入无效推理。\n";

        // 准备消息列表（需要关联历史上下文）
        final ChatMessage userMessage = ChatMessage.builder()
                .role(ChatMessageRole.USER)
                .content(message)   // 用户消息
                .build();
        List<ChatMessage> messages = new ArrayList<>();

        // 判断是否为首次对话
        if (!message.equals("开始") && globalMessageMap.isEmpty()) {
            throw new RuntimeException("请先开始游戏！");
        }
        if (message.equals("开始") && !globalMessageMap.containsKey(roomId)) {
            // - 首次对话，创建房间、初始化消息列表、添加系统预设
            final ChatMessage systemMessage = ChatMessage.builder()
                    .role(ChatMessageRole.SYSTEM)
                    .content(systemPrompt)   // 系统预设
                    .build();
            globalMessageMap.put(roomId, messages);
            messages.add(systemMessage);
        } else {
            // - 二次对话，读取过去的消息列表
            messages = globalMessageMap.get(roomId);
        }
        messages.add(userMessage);   // 写入用户消息

        // 调用AI
        String answer = aiManager.doChat(messages);
        final ChatMessage assistantMessage = ChatMessage.builder()
                .role(ChatMessageRole.ASSISTANT)
                .content(systemPrompt)
                .build();
        messages.add(assistantMessage);   // 消息列表追加

        // 返回信息
        if (answer.contains("over")) {
            System.out.println("游戏结束了，输入“开始”继续游戏");
            // 释放房间 id
            globalMessageMap.remove(roomId);
        }
        return answer;
    }

    /**
     * 获取房间列表
     *
     * @return 房间列表
     */
    @Override
    public List<ChatRoom> getChatRoomList() {
        List<ChatRoom> chatRoomList = new ArrayList<>();
        for (Map.Entry<Long, List<ChatMessage>> roomIdMessageListEntry : globalMessageMap.entrySet()) {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setRoomId(roomIdMessageListEntry.getKey());
            chatRoom.setChatMessageList(roomIdMessageListEntry.getValue());
            chatRoomList.add(chatRoom);
        }
        return chatRoomList;
    }
}