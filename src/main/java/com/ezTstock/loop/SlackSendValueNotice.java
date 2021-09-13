package com.ezTstock.loop;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.button;

public class SlackSendValueNotice {
    public void slackSendValueNotice(String subject_Name, String sub_pastVal, String subject_Val, String varianceVal, String subject_code){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        String isMinus = (Integer.parseInt(sub_pastVal)>Integer.parseInt(subject_Val))?"하락":"상승";
        String sub_Val = Double.toString(Double.parseDouble(varianceVal)/100);

        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .blocks(asBlocks(
                            section(s->s.text(markdownText("*[ "+subject_Name+" 가격변동 알림! ]*"))),
                            divider(),
                            section(se->se.text(plainText(subject_Name+"의 가격이 기존의 "+sub_pastVal+"에서 "+subject_Val+"로 "+sub_Val+"%만큼 "+isMinus+"하였습니다."))),
                            section(se->se.text(markdownText("우측 버튼을 누르면 네이버의 "+subject_Name+" 증시정보로 연결됩니다."))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url("https://finance.naver.com/item/main.nhn?code="+subject_code)))
                            ),
                            image(i->i
                                    .imageUrl("http://cichart.paxnet.co.kr/pax/chart/candleChart/V201716/paxCandleChartV201716Daily.jsp?abbrSymbol="+subject_code)
                                    .altText(subject_Name+"주식 그래프")
                            )
                    )));
            if (response.isOk()) {
                Message postedMessage = response.getMessage();
            } else {
                String errorCode = response.getError(); // e.g., "invalid_auth", "channel_not_found"
            }
        } catch (SlackApiException requestFailure) {
            // Slack API responded with unsuccessful status code (= not 20x)
        } catch (IOException connectivityIssue) {
            // Throwing this exception indicates your app or Slack servers had a connectivity issue.
        }
    }

}
