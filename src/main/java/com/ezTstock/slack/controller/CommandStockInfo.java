package com.ezTstock.slack.controller;

import com.ezTstock.parsing.CurrentStockInfo;
import com.ezTstock.slack.SlackSendMessage;
import com.ezTstock.slack.dto.SlashCommandRequest;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping
public class CommandStockInfo {
    @PostMapping(value = "/slack/command/stockInfo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandStockInfo(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/news' request: {}", dataPayload);
        String subject = dataPayload.toString().split("text=")[1].split(",")[0];
        CurrentStockInfo currentStockInfo = new CurrentStockInfo();
        SlackSendMessage sendMessage = new SlackSendMessage();
        try{
            String full_text = currentStockInfo.javaParsing(subject);
            sendMessage.slackSendMessage(full_text);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}