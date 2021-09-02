package com.ezTstock.parsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class StockNews {
    public String javaParsingNews(String subject) throws IOException {
        Document getCode = Jsoup.connect("https://www.ktb.co.kr/trading/popup/itemPop.jspx")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.152 Safari/537.36")
                .header("Accept-Language", "ko")
                .header("Accept_Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                .get();

        Elements elementC = getCode.select("select > option");

        String eleToStr = elementC.toString();
        String[] tmp1 = eleToStr.split(subject);
        String subject_code = tmp1[0].substring(tmp1[0].length() - 8, tmp1[0].length() - 2);

        Document searchDoc = Jsoup.connect("http://www.paxnet.co.kr/news/066570/stock?stockCode=066570&objId=S" + subject_code).get();

        String[] threeNews = {"","",""}; //상위 3개의 뉴스 획득용 배열
        int idx = 0;
        
        Elements newsHead = searchDoc.select("div > ul > li > dl > dt > a"); //기사 제목
        Elements newsBody = searchDoc.select("div > ul > li > dl > dd > a"); //기사 내용
        Elements newsFoot = searchDoc.select("div > ul > li > dl > dd > span"); //기사 정보

        for(Element head : newsHead){ //기사 제목 삽입
            if(idx>2) //3개만 가져올것
                break;
            else{
                String removeSpecial = head.text().replaceAll("\"","'");
                threeNews[idx] = threeNews[idx]+removeSpecial+"\n";
                idx++;
            }
        }
        
        idx = 0;
        for(Element body : newsBody){ //기사 내용 삽입
            if(idx>2)
                break;
            else{
                threeNews[idx] = threeNews[idx]+body.text()+"\n"+"http://www.paxnet.co.kr"+body.attr("href")+"\n";
                idx++;
            }
        }

        idx = 0;
        boolean subIdx = true;
        for(Element foot : newsFoot){ //기사 정보 삽입
            if(idx>2)
                break;
            else{
                if(subIdx){
                    threeNews[idx] = threeNews[idx]+foot.text()+" ";
                    subIdx=false;
                }
                else{
                    threeNews[idx] = threeNews[idx]+foot.text()+"\n";
                    subIdx=true;
                    idx++;
                }
            }
        }
        
        String news = "[ "+subject+" 관련 최신뉴스 ]\n";

        idx = 0;
        for(String concat : threeNews){
            if (idx > 1) {
                news = news+concat;
            }else{
                news = news+concat+"\n"; //"------------------------------------------------------------------------------------------------\n";
            }
            idx++;
        }

        return news;
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(javaParsingNews("LG전자"));
//    }
}
