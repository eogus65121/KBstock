package com.ezTstock.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CurrentStockInfo {
    public String []javaParsing(String subject) throws IOException {
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

        String[] returnVal = new String[8];
        returnVal[0] = "[ "+subject+" 주식 정보 ]";
        returnVal[1] = "-현재가: "+searchDoc.getElementById("_nowVal").text(); //현재가 추가
        String diff = searchDoc.getElementById("_diff").text(); // 증감치 tmp
        diff = changePM(diff);
        returnVal[2] = "-증감치: "+diff; //증감치 추가
        returnVal[3] = "-증감률: "+searchDoc.getElementById("_rate").text(); // 증감률 추가
        String[] tmp2 = searchDoc.toString().split("전일가");
        String[] tmp3 = tmp2[1].split("거래량");
        String[] tmp4 = tmp3[0].split("\n"); // tmp4[0] => 전일가
        String yesterday = tmp4[0];
        yesterday = yesterday.substring(1,yesterday.length());
        returnVal[4] = "-전일가: "+yesterday; // 전일가 추가
        returnVal[5] = "-거래량: "+searchDoc.getElementById("_quant").text(); // 거래량 추가
        returnVal[6] = "-거래대금: "+searchDoc.getElementById("_amount").text(); // 거래대금 추가
        returnVal[7] = "http://cichart.paxnet.co.kr/pax/chart/candleChart/V201716/paxCandleChartV201716Daily.jsp?abbrSymbol="+subject_code;

        return returnVal;
    }

    public String changePM(String diff){ // 상승과 하락으로 표시된 증감치에 대한 설명을 +와 -로 대치
        if(diff.startsWith("하락 "))
            diff = diff.replace("하락 ", "-");
        else if(diff.startsWith("상승 "))
            diff = diff.replace("상승 ", "+");
        return diff;
    }
    /*//출력문
    public static void main(String[] args) throws IOException {
        System.out.println(javaParsing("카카오"));
    }*/

}