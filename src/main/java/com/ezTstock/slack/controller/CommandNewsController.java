package com.ezTstock.slack.controller;

import com.ezTstock.parsing.StockNews;
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
public class CommandNewsController {
    SlackSendMessage sendMessage = new SlackSendMessage();
    StockNews stockNews = new StockNews();
    
    // 뉴스 내용 파싱 후 사용자에게 전송 기능
    @PostMapping(value = "/slack/command/news", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandNews(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/news' request: {}", dataPayload);
        String subject = dataPayload.getText().replaceAll(" ", "");
        if(subject.equals("")){
            sendMessage.slackSendMessage("종목을 입력해주세요.");
        }else{
            try{
                String[] news_text = stockNews.javaParsingNews(subject);
                sendMessage.slackSendMessage(news_text);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
