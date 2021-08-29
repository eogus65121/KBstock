package com.ezTstock.slack;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SlackPostMessage {
    /**
     * Post a message to a channel your app is in using ID and message text
     */
    static void slackPostMessage(String text) {
        // you can get this instance via ctx.client() in a Bolt app
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("token");
        String channel_id = slack_json.readJ("channel");
        String app_name = slack_json.readJ("app_name");
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger(app_name);
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(token)
                            .channel(channel_id)
                            .text(text)
                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }
/*
    public static void main(String[] args) throws Exception {
        slackPostMessage("post message test");
    }
 */
}
