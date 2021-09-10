package com.loop;

import com.ezTstock.slack.dto.UserRequirementDto;
import com.ezTstock.slack.dto.VarianceValueDto;
import com.ezTstock.slack.service.impl.UserProfileSvcImpl;
import com.ezTstock.slack.service.impl.VarianceValueSvcImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Server {
    @Autowired
    UserProfileSvcImpl require;
    @Autowired
    VarianceValueSvcImpl user_subjects;
    @Autowired
    CheckNowValue CNV;
    @Autowired
    SlackSendValueNotice SSVN;

    public void Loop() throws Exception {
        while(true){
            require = new UserProfileSvcImpl();
            List<UserRequirementDto> require_on = require.selectRequirementData();//db에서 requirement가 on인 사람들을 가져오기

            for(UserRequirementDto user : require_on){
                String user_name = user.getName();
                List<VarianceValueDto> subject_List = user_subjects.selectServerData(user_name);

                for(VarianceValueDto subject : subject_List){
                    String subject_name = subject.getSubject_name();
                    String current = subject.getCurrent();
                    if(subject.getCurrent()==null){//만약 기존 가격이 null이면 파싱 후 db에 해당 종목 현재가 수정
                        String input_Val = CNV.checkStockValue(subject_name);
                        user_subjects.updateVarianceCurrent(input_Val, subject_name, subject.getUser_name());//db 업데이트 (new current)
                    }else{//[0]=0 or 1, [1]=현재가, [2]=변동치*100, [3]=종목코드
                        String[] isChanged = CNV.checkStockValue(subject_name, Integer.parseInt(current), Double.parseDouble(subject.getValue()));

                        if(isChanged[0].equals("1")){//설정한 변동치보다 더 변했음
                            user_subjects.updateVarianceValue(subject_name, user_name, isChanged[1]);//db의 해당 주가 정보를 수정(해당 종목의 현재가만)

                            double ratio = Double.parseDouble(isChanged[2])/100;
                            //slack 사용자에게 가격 변동에 대한 메시지 전송 (종목명, 종목 기존가, 종목 현재가, 종목 변동치, 종목 코드)
                            SSVN.slackSendValueNotice(subject_name, current, isChanged[1], Double.toString(ratio), isChanged[3]);
                        }
                    }

                }
            }
            Thread.sleep(2000); //5분 대기 300000
        }
    }

    public static void main(String[] args) throws Exception {
        Server tmp = new Server();
        tmp.Loop();
    }
}