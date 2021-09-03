package com.ezTstock.slack.slashController;

import com.ezTstock.parsing.CurrentStockInfo;
import com.ezTstock.slack.function.SlackSendImage;
import com.ezTstock.slack.function.SlackSendMessage;
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
        String subject = dataPayload.toString().split("text=")[1].split(",")[0].replaceAll(" ", "");
        CurrentStockInfo currentStockInfo = new CurrentStockInfo();
        SlackSendMessage sendMessage = new SlackSendMessage();
        SlackSendImage sendImage = new SlackSendImage();

        try{
            String [] param = currentStockInfo.javaParsing(subject);
            sendMessage.slackSendMessage(param[0]);
            sendImage.slackSendImage(param[1]);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}