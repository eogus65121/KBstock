package com.ezTstock.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CurrentStockInfo {
    static void javaParsing(String subject) throws IOException {
        Document getCode = Jsoup.connect("https://www.ktb.co.kr/trading/popup/itemPop.jspx")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.152 Safari/537.36")
                .header("Accept-Language", "ko")
                .header("Accept_Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .get();

        Elements elementC = getCode.select("select > option");

        String eleToStr = elementC.toString();
        String[] tmp = eleToStr.split(subject);
        String subject_code = tmp[0].substring(tmp[0].length()-8,tmp[0].length()-2);

        Document searchDoc = Jsoup.connect("https://finance.naver.com/search/searchList.nhn?query="+subject_code).get();

        //Elements elementImg = searchDoc.select("div >");

        System.out.println(searchDoc);
    }
      //출력문
    public static void main(String[] args) throws IOException {
        javaParsing("LG전자");
    }

}