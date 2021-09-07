package com.loop;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CheckNowValue {
    public String[] checkStockValue(String subject, int compVal, double expectDiff) throws IOException {
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
        double diff = trimedVal -compVal; //현재가-기준가
        diff = Math.round((diff/compVal)*10000)/100.0;
        System.out.println(diff);
        String[] returnVal = {"0","","", subject_code};

        if(Math.abs(diff)>=Math.abs(expectDiff)){
            returnVal[0] = "1";
        }
        returnVal[1] = Integer.toString((int)trimedVal);
        returnVal[2] = Integer.toString((int)(diff*100));

        return returnVal;
    }

    /*
    public static void main(String[] args) throws Exception {
        String[] tmp = checkStockValue("LG전자", 170000, 0.5);
    }*/
}