package com.loop;

public class Server {
    public void Loop() throws InterruptedException {
        while(true){
            //db에서 requirement가 on인 사람들을 가져오고(List) 그 만큼 반복 [db쿼리(select) 필요]

            //(반복문 내)각 사람이 입력해둔 [ 종목과 기존가격, 변동퍼센트(사용자가 입력한) ]들을 가져오기 [db쿼리(select) 필요]

            //만약 기존 가격이 null이다 그러면 파싱 후 db에 해당 종목 현재가 수정 [db쿼리(update) 필요]

            //만약 CheckNowValue의 반환 String 배열의 첫 인덱스 값이 "1"이라면 [ equals("1") ] > 너가 설정한 변동치보다 더 변했어

            //db의 해당 주가 정보를 수정(해당 종목의 현재가, 변동치를 충족할 때만 수정됨) [db쿼리(update) 필요]

            //slackSendValueNotice();//slack 사용자에게 가격 변동에 대한 메시지 전송 (종목명, 종목 기존가, 종목 현재가, 종목 변동치, 종목 코드)

            Thread.sleep(300000); //5분 대기
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Server tmp = new Server();
        tmp.Loop();
    }
}
