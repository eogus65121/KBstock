package com.ezTstock.slack.function;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ReplyMessage {

    static void replyMessage(String ts) {
        // you can get this instance via ctx.client() in a Bolt app
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(token)
                            .channel(channel)
                            .threadTs(ts)
                            .text("Hello again :wave:")
                    // You could also use a blocks[] array to send richer content
            );
            // Print result
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }
    /*
    public static void main(String[] args) throws Exception {
        // Uses a known channel ID and message TS
        replyMessage("1630425820.000500");
    }
     */
}
