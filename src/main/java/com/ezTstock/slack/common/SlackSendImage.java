package com.ezTstock.slack.common;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;

public class SlackSendImage {
    public static void slackSendImage(String[] stockInfo){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)

                    .blocks(asBlocks(
                            section(s->s.text(markdownText(stockInfo[0]))),
                            divider(),
                            section(s->s.text(plainText(stockInfo[1]))),
                            section(s->s.text(plainText(stockInfo[2]))),
                            section(s->s.text(plainText(stockInfo[3]))),
                            section(s->s.text(plainText(stockInfo[4]))),
                            section(s->s.text(plainText(stockInfo[5]))),
                            section(s->s.text(plainText(stockInfo[6]))),
                            image(q -> q
                                    .imageUrl(stockInfo[7])
                                    .altText("현재 주식 동향")
                            ),
                            divider()
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
