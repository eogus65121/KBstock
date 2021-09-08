package com.loop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    public void Loop() throws InterruptedException, IOException {
        while(true){
            //db에서 requirement가 on인 사람들을 가져오고(List) 그 만큼 반복 [db쿼리(select) 필요]
            //
            URL obj = new URL("http://1e50-121-150-231-94.ngrok.io/server/data?user_name=eogus6512");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

            String[] tmp = in.readLine();
            System.out.println(in.readLine());
            StringBuilder sb = new StringBuilder();
//            Map<String,String> line = new HashMap<>();
//
//            sb.append(line);
//
//            JSONObject json = new JSONObject(sb.toString());





            //(반복문 내)각 사람이 입력해둔 [ 종목과 기존가격, 변동퍼센트(사용자가 입력한) ]들을 가져오기 [db쿼리(select) 필요]

            //만약 기존 가격이 null이다 그러면 파싱 후 db에 해당 종목 현재가 수정 [db쿼리(update) 필요]

            //만약 CheckNowValue의 반환 String 배열의 첫 인덱스 값이 "1"이라면 [ equals("1") ] > 너가 설정한 변동치보다 더 변했어

            //db의 해당 주가 정보를 수정(해당 종목의 현재가, 변동치를 충족할 때만 수정됨) [db쿼리(update) 필요]

            //slackSendValueNotice();//slack 사용자에게 가격 변동에 대한 메시지 전송 (종목명, 종목 기존가, 종목 현재가, 종목 변동치, 종목 코드)

            Thread.sleep(2000); //5분 대기 300000
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Server tmp = new Server();
        tmp.Loop();
    }
}
