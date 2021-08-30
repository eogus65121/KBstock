package com.ezTstock.slack;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SlackSendMessage {
    static void sendMessage(String text){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("token");
        String channel_id = slack_json.readJ("channel");
        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel_id)
                    .text(text));
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

    /* 출력용
    public static void main(String[] args) throws Exception {
        sendMessage("new test message");
    }
     */

}
