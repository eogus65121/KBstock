package com.ezTstock.slack.controller;

import com.ezTstock.parsing.CurrentStockInfo;
import com.ezTstock.slack.common.SlackSendImage;
import com.ezTstock.slack.common.SlackSendMessage;
import com.ezTstock.slack.dto.SlashCommandRequestDto;
import org.springframework.http.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping
public class CommandStockInfoController {
    CurrentStockInfo currentStockInfo = new CurrentStockInfo();
    SlackSendMessage sendMessage = new SlackSendMessage();
    SlackSendImage sendImage = new SlackSendImage();

    @PostMapping(value = "/slack/command/stockInfo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandStockInfo(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/stockInfo' request: {}", dataPayload);
        String subject = dataPayload.getText().replaceAll(" ", "");
        if(subject.equals("")){
            sendMessage.slackSendMessage("종목을 입력해주세요.");
        }else {
            try {
                String[] param = currentStockInfo.javaParsing(subject);
                sendImage.slackSendImage(param);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}