package com.ezTstock.slack.slashController;

import com.ezTstock.stock_db.model.userProfile;
import com.ezTstock.slack.dto.SlashCommandRequest;
import com.ezTstock.slack.function.ConversationsHistory;
import com.ezTstock.slack.function.SlackSendMessage;
import com.ezTstock.stock_db.controller.UserProfileController;
import com.ezTstock.stock_db.urlRequest.UrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
@RequestMapping
public class CommandRTVariance {
    SlackSendMessage sendMessage = new SlackSendMessage();
    ConversationsHistory conversationHistory = new ConversationsHistory();
    UrlRequest urlRequest = new UrlRequest();

    @PostMapping(value = "/slack/command/RTVariance", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandRTVariance1(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/RTVariance' request: {}", dataPayload);

        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();

        try{
            if(urlRequest.user_get_name(user_name) == null){
                urlRequest.user_add_user(user_name);
                System.out.println("추가 성공");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        if(text.equals("")){
            sendMessage.slackSendMessage("실시간 변동 알림 기능입니다. 계정이 없는 경우 자동으로 계정이 추가됩니다.\n"
            + "** 알림 기능 관련 명령어 **\n" + "'/add' : 종목 추가하기 \n" + "'/remove' : 종목 제거하기 \n"
            + "'/rtv (on/off) : 실시간 알림 (켜기/끄기)\n" + "'/subject' : 실시간 알림 종목 종류");
        }else if(text.equals("on")){
            // 실시간 알림 켜기
            // db에 해당 계정 rtv on
            sendMessage.slackSendMessage("실시간 알림이 켜졌습니다.");
        } else if(text.equals("off")){
            // 실시간 알림 끄기
            // db에 해당 계정 rtv off
            sendMessage.slackSendMessage("실시간 알림이 꺼졌습니다.");
        } else{
            sendMessage.slackSendMessage("지정된 명령어가 없습니다. 다시 입력해주세요.");
        }
    }

    @PostMapping(value = "/slack/command/RTVAdd", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash add
    public void CommandRTVAdd(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/RTVAdd' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        /*
        계정이 없습니다 '/rtv'를 실행해서 계정을 추가하세요
        원하는 종목 명
        변동 수치
         */
    }

    @PostMapping(value = "/slack/command/RTVRemove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash remove
    public void CommandRTVRemove(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/RTVRemove' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        /*
        계정이 없습니다 '/rtv'를 실행해서 계정을 추가하세요
        원하는 종목 명
         */

    }
}
