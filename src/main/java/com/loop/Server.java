package com.loop;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    CheckNowValue CNV;
    SlackSendValueNotice SSVN;
    public void Loop() throws InterruptedException, IOException, ParseException {
        while(true){
            //db에서 requirement가 on인 사람들을 가져오고 String[]



            // 그 배열만큼 반복 [db쿼리(select) 필요]@@@@@@@@@@@@@@






            //(반복문 내)각 사람에 대해 그 사람이 입력해둔 [ 종목과 기존가격, 변동퍼센트(사용자가 입력한) ]들을 가져오기 [db쿼리(select) 완료]
            URL obj = new URL("http://1e50-121-150-231-94.ngrok.io/server/data?user_name=eogus6512");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String tmp = in.readLine();
            System.out.println(tmp);
            tmp = tmp.substring(1,tmp.length()-1); //대괄호 제거
            tmp = tmp.replaceAll("},","} , ");

            String[] tmp2 = tmp.split(" , ");
            for(String str :tmp2){
                JSONParser parser = new JSONParser();
                Object ob = parser.parse(str);
                JSONObject jo = (JSONObject) ob;

                String subject_name = (String) jo.get("subject_name");
                String current = (String) jo.get("current");
                String value = (String) jo.get("value");


                if(current==null){//만약 기존 가격이 null이면 파싱 후 db에 해당 종목 현재가 수정
                    String input_Val = CNV.checkStockValue(subject_name);
                    //db 업데이트 코드 (new current)@@@@@@@@@
                }else{//[0]=0 or 1, [1]=현재가, [2]=변동치*100, [3]=종목코드
                    String[] isChanged = CNV.checkStockValue(subject_name, Integer.parseInt(current), Double.parseDouble(value));

                    if(isChanged[0].equals("1")){//설정한 변동치보다 더 변했음
                        //db의 해당 주가 정보를 수정(해당 종목의 현재가만) [db쿼리(update) 필요]@@@@@@@@@@@@@@@

                        double ratio = Double.parseDouble(isChanged[2])/100;
                        //slack 사용자에게 가격 변동에 대한 메시지 전송 (종목명, 종목 기존가, 종목 현재가, 종목 변동치, 종목 코드)
                        SSVN.slackSendValueNotice(subject_name, current, isChanged[1], Double.toString(ratio), isChanged[3]);
                    }
                }
            }
            Thread.sleep(2000); //5분 대기 300000
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        Server tmp = new Server();
        tmp.Loop();
    }
}