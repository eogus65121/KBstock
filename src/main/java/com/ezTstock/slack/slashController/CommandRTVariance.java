package com.ezTstock.slack.slashController;

import com.ezTstock.slack.dto.SlashCommandRequest;
import com.ezTstock.slack.function.ConversationsHistory;
import com.ezTstock.slack.function.SlackSendMessage;
import com.ezTstock.stock_db.urlRequest.UrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
            + "'/rtv (on/off) : 실시간 알림 (켜기/끄기) (기본설정 off)\n" + "'/subject' : 실시간 알림 종목 종류");
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

        try{
            if(urlRequest.user_get_name(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                sendMessage.slackSendMessage("원하는 종목명과 변동 수치를 입력 후 '/done add'를 입력해주세요");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/slack/command/RTVRemove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash remove
    public void CommandRTVRemove(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/RTVRemove' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();
        try{
            if(urlRequest.user_get_name(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else if(text == null){
                sendMessage.slackSendMessage("입력값이 없습니다. '/rtv 종목명' 으로 다시 입력해주세요");
            } else{
                //제거코드
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @PostMapping(value="slack/command/RTVSubject", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVSubject(SlashCommandRequest dataPayload){
        log.info("Request 'POST /slack/command/RTVSubject' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        sendMessage.slackSendMessage("*실시간 종목 출력 기능입니다*");
        try{
            if(urlRequest.user_get_name(user_name) == null) {
                sendMessage.slackSendMessage( "계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                sendMessage.slackSendMessage(urlRequest.variance_get_userSubject(user_name));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @PostMapping(value="slack/command/done", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVHistory(SlashCommandRequest dataPayload) {
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();
        String [] history = conversationHistory.fetchHistory(); // 0 : 종목, 1 : value
        if(text.equals("add")){
            sendMessage.slackSendMessage("종목 추가를 시작합니다.");
        }
        try {
            urlRequest.variance_insert_value(history[0], user_name,history[1]);
            sendMessage.slackSendMessage( "추가 성공하였습니다.");
        }catch(IOException e){
            sendMessage.slackSendMessage( "에러 발생");
            e.printStackTrace();
        }
    }
}
