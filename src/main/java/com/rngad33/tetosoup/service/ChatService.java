package com.rngad33.tetosoup.service;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import java.util.List;
import java.util.Map;

/**
 * 客户端服务接口
 */
public interface ChatService {

    /**
     * 与 AI 对话
     *
     * @param roomId   房间号
     * @param message 用户输入的信息
     * @return 返回结果
     */
    String doChat(long roomId, String message);

    /**
     * 获取房间列表
     *
     * @return
     */
    List<Map<Long, List<ChatMessage>>> getChatRoomList();

}