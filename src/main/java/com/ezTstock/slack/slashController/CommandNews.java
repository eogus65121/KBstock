package com.ezTstock.slack.slashController;

import com.ezTstock.parsing.StockNews;
import com.ezTstock.slack.dto.SlashCommandRequest;
import com.ezTstock.slack.function.SlackSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping
public class CommandNews {
    @PostMapping(value = "/slack/command/news", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandNews(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/news' request: {}", dataPayload);
        String subject = dataPayload.toString().split("text=")[1].split(",")[0].replaceAll(" ", "");
        SlackSendMessage sendMessage = new SlackSendMessage();
        StockNews stockNews = new StockNews();

        try{
            String[] news = stockNews.javaParsingNews(subject);
            sendMessage.slackSendMessage(news);
        }catch(IOException e){
            e.printStackTrace();
        }

    }
}
