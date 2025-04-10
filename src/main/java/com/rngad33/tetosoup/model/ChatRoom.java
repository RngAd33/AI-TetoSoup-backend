package com.rngad33.tetosoup.model;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.Data;
import java.util.List;

/**
 * 聊天室模型
 */
@Data
public class ChatRoom {

    /**
     * 房间号
     */
    private Long roomId;

    /**
     * 消息列表
     */
    private List<ChatMessage> chatMessageList;

}