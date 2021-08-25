package com.ezTstock.slack;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SlackWebhookSend {
    @GetMapping("/send")
    public void send(){
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("username", "KBstockChatBot");
        request.put("text", "webhook message sender test hhh");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(request);

        String url = "https://hooks.slack.com/services/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";

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    }
}
