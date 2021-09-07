package com.loop;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

public class SlackSendValueNotice {
    public static void slackSendMessage(String subject_Name, String sub_pastVal, String subject_Val, String varianceVal, String subject_code){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        boolean isMinus = varianceVal.startsWith("-")?true:false; //

        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .blocks(asBlocks(
                            section(s->s.text(markdownText("*[ "+subject_Name+" 가격변동 알림! ]*"))),
                            actions(a->a
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

    /*
    public static void main(String[] args) throws Exception {
        slackSendValueNotice("test");
    }

    */


}
