package com.rngad33.tetosoup.controller;

import com.rngad33.tetosoup.model.ChatRoom;
import com.rngad33.tetosoup.service.ChatService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * 客户端接口层
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    /**
     * 与 AI 进行对话
     *
     * @param roomId  房间号
     * @param message 用户输入的消息
     * @return AI 的回复
     */
    @PostMapping("/doChat/{roomId}")
    public String doChat(@PathVariable long roomId, @RequestParam String message) {
        return chatService.doChat(roomId, message);
    }

    /**
     * 获取所有聊天房间列表
     *
     * @return 聊天房间列表
     */
    @GetMapping("/getChatRoomList")
    public List<ChatRoom> getChatRoomList() {
        return chatService.getChatRoomList();
    }
}
