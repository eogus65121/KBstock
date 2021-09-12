package com.ezTstock.slack.common;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RetrievingMessage {

    static String fetchMessage() {
        // you can get this instance via ctx.client() in a Bolt app
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        String ts = slack_json.readJ("ts");
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            // Call the chat.postMessage method using the built-in WebClient
            var result = client.conversationsHistory(r -> r
                    // The token you used to initialize your app
                    .token(token)
                    .channel(channel)
                    // In a more realistic app, you may store ts data in a db
                    .latest(ts)
                    // Limit results
                    .inclusive(true)
                    .limit(1)
            );

            var message = result.getMessages().get(0);
            // Print message text
            logger.info("result {}", message.getText());
            return message.getText();
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
        return "";
    }
}