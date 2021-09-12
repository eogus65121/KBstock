package com.ezTstock.slack.controller;

import com.ezTstock.slack.dto.SlashCommandRequestDto;
import com.ezTstock.slack.common.ConversationsHistory;
import com.ezTstock.slack.common.SlackSendMessage;
import com.ezTstock.slack.dto.VarianceValueDto;
import com.ezTstock.slack.service.UserProfileSvc;
import com.ezTstock.slack.service.VarianceValueSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                sendMessage.slackSendMessage("자동 계정 추가 성공");
            }
        }catch (Exception e){
            sendMessage.slackSendMessage("자동 계정 추가 실패, 재시도 바랍니다.");
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
            sendMessage.slackSendMessage("rtv명령어 에러 발생, 재시도 바랍니다.");
            e.printStackTrace();
        }
    }

    // 실시간 알림을 원하는 종목명, 변동 수치를 추가하는 기능1 : ok
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
            sendMessage.slackSendMessage("추가 에러 발생 재시도 바랍니다.");
            e.printStackTrace();
        }
    }

    @PostMapping(value="/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVDelete(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/update' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();

        try{
            if(userSvc.getUserName(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                sendMessage.slackSendMessage("수정을 원하는 종목명과 변동 수치를 입력 후 '/done update'를 입력해주세요");
            }
        }catch(Exception e){
            sendMessage.slackSendMessage("수정 에러 발생 재시도 바랍니다.");
            e.printStackTrace();
        }
    }


    // 등록된 실시간 정보 서비스에서 원하는 종목 제거 : ok
    @PostMapping(value = "/RTVRemove", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE) // slash remove
    public void CommandRTVRemove(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVRemove' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText().replaceAll(" ", "");
        try{
            if(userSvc.getUserName(user_name) == null){
                sendMessage.slackSendMessage("계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else {
                if(text == null){
                    sendMessage.slackSendMessage("입력값이 없습니다. '/rtv 종목명' 으로 다시 입력해주세요");
                } else{
                    sendMessage.slackSendMessage(text + " 종목 제거를 시작합니다.");
                    variableSvc.deleteVarianceValue(text, user_name);
                    sendMessage.slackSendMessage("제거가 완료되었습니다.");
                }
            }
        }catch (Exception e){
            sendMessage.slackSendMessage("제거 에러발생 재시도 바랍니다.");
            e.printStackTrace();
        }
    }

    // 실시간 종목 출력 : ok
    @PostMapping(value="/RTVSubject", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVSubject(SlashCommandRequestDto dataPayload){
        log.info("Request 'POST /slack/command/RTVSubject' request: {}", dataPayload);
        String user_name = dataPayload.getUser_name();
        sendMessage.slackSendMessage("실시간 종목 출력 기능입니다");
        try{
            if(userSvc.getUserName(user_name) == null) {
                sendMessage.slackSendMessage( "계정이 없습니다. '/rtv'를 입력하여 계정을 추가해주세요");
            }else{
                List<VarianceValueDto> varianceValueList =  variableSvc.selectServerData(user_name); // String 가공할 코드 필요
                StringBuilder sb = new StringBuilder();
                sb.append(user_name +"님의 실시간 종목 정보입니다.\n");
                for(int i = 0; i< varianceValueList.size(); i++){
                    sb.append(varianceValueList.get(i).getSubject_name() + " : ");
                    sb.append(varianceValueList.get(i).getValue() + "\n");
                }
                sendMessage.slackSendMessage(sb.toString());
            }
        }catch (Exception e){
            sendMessage.slackSendMessage("에러 발생, 재시도 바랍니다.");
            e.printStackTrace();
        }
    }

    // 사용자 입력값을 받아와 db에 추가하는 기능 : ok
    @PostMapping(value="/done", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandRTVHistory(SlashCommandRequestDto dataPayload) {
        String user_name = dataPayload.getUser_name();
        String text = dataPayload.getText().replaceAll(" ", "");
        String [] history = conversationHistory.fetchHistory(); // 0 : subjectName, 1 : value

        if(text.equals("add")){
            sendMessage.slackSendMessage("종목 추가를 시작합니다.");
            try {
                variableSvc.insert(history[0], user_name, history[1], null);
                sendMessage.slackSendMessage( "추가 성공");
            }catch(Exception e){
                sendMessage.slackSendMessage( "추가 에러 발생 재시도 바랍니다.");
                e.printStackTrace();
            }
        }else if(text.equals("update")){
            sendMessage.slackSendMessage("종목 수정을 시작합니다.");
            try{
                variableSvc.updateVarianceValue(history[0], user_name, history[1]);
                sendMessage.slackSendMessage( "수정 성공");
            }catch(Exception e){
                sendMessage.slackSendMessage( "수정 에러 발생, 종목 입력을 정확히 기입해주세요");
                e.printStackTrace();
            }
        }
    }
    
    // slash 전체 명령어 및 설명 : ok
    @PostMapping(value="/all", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void CommandAll(){
        String str = "*'/stockinfo 종목이름'*  :  입력한 종목의 실시간 주가 안내\n" +
                "*'/news 종목이름'*  :  입력한 종목과 관련된 최근 뉴스 안내\n" +
                "*'/rtv'*  :  사용자 계정 자동 등록 + '/rtv'* 관련 명령어 안내 \n"+
                "*'/rtv (on/off)'*  :  사용자 실시간 알림 서비스 on/off\n"+
                "*'/subject'*  :  본인이 등록한 실시간 알림 종목 및 변동값 안내\n" +
                "*'/add' + '/done add'*  :  원하는 종목을 실시간 알림 목록에 추가\n" +
                "*'/update' + '/done update'*  :  실시간 알림의 종목중 원하는 변동값 수치 변경\n"+
                "*'/delete 종목이름'*  :  실시간 알림의 종목중 입력한 종목을 제거";
        try{
            sendMessage.slackSendMessage(str);
        }catch(Exception e){
            sendMessage.slackSendMessage("에러 발생, 재시도 바랍니다.");
            e.printStackTrace();
        }
    }
}
