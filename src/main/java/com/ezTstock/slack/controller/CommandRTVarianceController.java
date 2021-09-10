package com.ezTstock.slack.controller;

import com.ezTstock.slack.dto.SlashCommandRequestDto;
import com.ezTstock.slack.common.ConversationsHistory;
import com.ezTstock.slack.common.SlackSendMessage;
import com.ezTstock.slack.service.UserProfileSvc;
import com.ezTstock.slack.service.VarianceValueSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/slack/command")
public class CommandRTVarianceController {
    SlackSendMessage sendMessage = new SlackSendMessage();
    ConversationsHistory conversationHistory = new ConversationsHistory();

    @Autowired
    private VarianceValueSvc variableSvc;

    @Autowired
    private UserProfileSvc userSvc;

    // 계정 추가 및 requirement on / off  : ok
    @PostMapping(value = "/RTVariance", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void commandRTVariance1(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVariance' request: {}", dataPayload);

        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();

        try{
            if(userSvc.getUserName(user_name) == null){
                // requirement 기본 설정 off
                userSvc.insertUserProfile(user_name, "off");
                System.out.println("계정 추가 성공");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            // parameter X
            if (text.equals("")) {
                sendMessage.slackSendMessage("실시간 변동 알림 기능입니다. 계정이 없는 경우 자동으로 계정이 추가됩니다.\n"
                        + "** 알림 기능 관련 명령어 **\n" + "'/add' : 종목 추가하기 \n" + "'/remove' : 종목 제거하기 \n"
                        + "'/rtv (on/off) : 실시간 알림 (켜기/끄기) (기본설정 off)\n" + "'/subject' : 실시간 알림 종목 종류");
                // parameter on
            } else if (text.equals("on")) {
                userSvc.updateUserRequirement(text, user_name);
                sendMessage.slackSendMessage("실시간 알림이 켜졌습니다.");
                // parameter off
            } else if (text.equals("off")) {
                userSvc.updateUserRequirement(text, user_name);
                sendMessage.slackSendMessage("실시간 알림이 꺼졌습니다.");
                // 지정된 parameter 값이 없는 경우
            } else {
                sendMessage.slackSendMessage("지정된 명령어가 없습니다. 다시 입력해주세요.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // 실시간 알림을 원하는 종목명, 변동 수치를 추가하는 기능1
    @PostMapping(value = "/RTVAdd", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash add
    public void CommandRTVAdd(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVAdd' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();

        try{
            if(userSvc.getUserName(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                sendMessage.slackSendMessage("원하는 종목명과 변동 수치를 입력 후 '/done add'를 입력해주세요");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // ??
    @PostMapping(value = "/RTVRemove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash remove
    public void CommandRTVRemove(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVRemove' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();
        try{
            if(userSvc.getUserName(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else if(text == null){
                sendMessage.slackSendMessage("입력값이 없습니다. '/rtv 종목명' 으로 다시 입력해주세요");
            } else{
                //제거코드
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // error
    @PostMapping(value="/RTVSubject", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVSubject(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVSubject' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        sendMessage.slackSendMessage("*실시간 종목 출력 기능입니다*");
        try{
            if(userSvc.getUserName(user_name) == null) {
                sendMessage.slackSendMessage( "계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                variableSvc.selectServerData(user_name); // String 가공할 코드 필요

                sendMessage.slackSendMessage("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 사용자 입력값을 받아와 db에 추가하는 기능
    @PostMapping(value="/done", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVHistory(SlashCommandRequestDto dataPayload) {
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText();
        String [] history = conversationHistory.fetchHistory(); // 0 : subjectName, 1 : value

        if(text.equals("add")){
            sendMessage.slackSendMessage("종목 추가를 시작합니다.");
        }

        try {
            variableSvc.insert(history[0], user_name, history[1], null);
            sendMessage.slackSendMessage( "추가 성공하였습니다.");
        }catch(Exception e){
            sendMessage.slackSendMessage( "에러 발생 재시도 바랍니다.");
            e.printStackTrace();
        }
    }
}
