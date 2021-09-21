package com.ezTstock.loop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CheckNowValue {
    public String checkStockValue(String subject) throws IOException {
        Document getCode = Jsoup.connect("https://www.ktb.co.kr/trading/popup/itemPop.jspx")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.152 Safari/537.36")
                .header("Accept-Language", "ko")
                .header("Accept_Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .get();

        Elements elementC = getCode.select("select > option");

        String eleToStr = elementC.toString();
        String[] tmp1 = eleToStr.split(subject);
        String subject_code = tmp1[0].substring(tmp1[0].length()-8,tmp1[0].length()-2);

        Document searchDoc = Jsoup.connect("https://finance.naver.com/item/sise.nhn?code="+subject_code).get(); // 네이버> http://www.paxnet.co.kr/stock/analysis/main?abbrSymbol=

        String nowVal = searchDoc.getElementById("_nowVal").text();
        nowVal = nowVal.replaceAll(",",""); //쉼표 제거
        double trimedVal = Double.parseDouble(nowVal); //현재가 가져오기

        return Double.toString(trimedVal);
    }

    //compVal = db가격과 비교할 현재가, targetDiff = db가격 대비 현재가의 증감치(%)
    public String[] checkStockValueArr(String subject, double compVal, double targetDiff) throws IOException {
        Document getCode = Jsoup.connect("https://www.ktb.co.kr/trading/popup/itemPop.jspx")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.152 Safari/537.36")
                .header("Accept-Language", "ko")
                .header("Accept_Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .get();

        Elements elementC = getCode.select("select > option");

        String eleToStr = elementC.toString();
        String[] tmp1 = eleToStr.split(subject);
        String subject_code = tmp1[0].substring(tmp1[0].length()-8,tmp1[0].length()-2);

        Document searchDoc = Jsoup.connect("https://finance.naver.com/item/sise.nhn?code="+subject_code).get(); // 네이버> http://www.paxnet.co.kr/stock/analysis/main?abbrSymbol=

        String nowVal = searchDoc.getElementById("_nowVal").text();
        nowVal = nowVal.replaceAll(",",""); //쉼표 제거
        int trimedVal = Integer.parseInt(nowVal)*100; //현재가 가져오기 (현재가*100은 double 형변환 후 나누어 소수점 둘째자리까지 이용)
        int variation_Val = Math.abs(trimedVal-(int)compVal*100); //현재가-기존가 (*100 상태)
        double realDiff = ((double)variation_Val/compVal)*100; // (현재가-기존가)를 기존가로 나눈 증감치 (*100 상태)
        String[] returnVal = {"0","","", subject_code};

        if(Math.abs((int)(realDiff))>=Math.abs((int)(targetDiff*100)))//실제 증감치가 목표 증감치보다 같거나 크다면
            returnVal[0] = "1"; //슬랙에 메시지를 전송을 위한
        returnVal[1] = Integer.toString(trimedVal/100); //현재가 입력 (곱해준 100 정상화)
        returnVal[2] = Integer.toString((int)(realDiff)); //double>int>String 형변환은 소숫점을 문자열에 미포함하기 위함

        return returnVal; //test
    }
}
