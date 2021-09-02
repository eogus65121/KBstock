package com.ezTstock.slack;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;

public class SlackSendImage {

    public static void slackSendImage(String img_url){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        System.out.println(img_url);
        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .blocks(asBlocks(
                            image(q -> q
                                    .imageUrl(img_url)
                                    .altText("현재 주식 동향")
                            )
                            )
                    ));
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
        slackSendMessage("test");
    }
*/
}
