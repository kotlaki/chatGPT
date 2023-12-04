package com.example.chatgpt.dto;

import com.example.chatgpt.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
    }
}
