package com.ezTstock.slack.slashController;

import com.ezTstock.slack.function.SlackSendMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandRTVariance {
    SlackSendMessage sendMessage = new SlackSendMessage();
    @PostMapping("/slack/command/RTVariance")
    public void commandRTVariance(){
        // 웹 로그인 환경 개발하기
        sendMessage.slackSendMessage("실시간 변동 검사 기능입니다. \n" + "종목명 \n" + "변동 수치 \n"
                + "위의 정보를 순서대로 입력 후 '/rtv2' 명령어를 입력해주세요.");
    }
}
