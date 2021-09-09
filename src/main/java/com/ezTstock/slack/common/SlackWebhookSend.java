package com.ezTstock.slack.common;

import com.ezTstock.config.SlackImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SlackWebhookSend {
    @GetMapping("/webHookSend")
    public void send(){
        RestTemplate restTemplate = new RestTemplate();
        SlackImpl webHook = new SlackImpl();
        String url = webHook.readJ("webHookUrl");

        Map<String, Object> request = new HashMap<>();
        request.put("username", "KBstockChatBot");
        request.put("text", "webhook message sender test hhh");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(request);

        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

    }
}
