package com.example.chatgpt.controller;

import com.example.chatgpt.dto.ChatResponse;
import com.example.chatgpt.model.ChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GPT35Controller {

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;
//    private static final String apiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions";

    @GetMapping(value = "/chat")
    public String complete(@RequestParam String prompt) {
        ChatRequest chatRequest = new ChatRequest(model, prompt);
        chatRequest.setN(1);
        chatRequest.setTemperature(1);

        ChatResponse chatResponse = restTemplate.postForObject(apiUrl, chatRequest, ChatResponse.class);

        if (chatResponse == null || chatResponse.getChoices() == null || chatResponse.getChoices().isEmpty()) {
            return "No response";
        }
        return chatResponse.getChoices().get(0).getMessage().getContent();
    }
}
