package com.ezTstock.slack;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Message;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

public class ConversationsHistory {

    static Optional<List<Message>> conversationHistory = Optional.empty();

    public static String fetchHistory() {
        SlackImpl slack_json = new SlackImpl();
        String bot_token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");
        var client = Slack.getInstance().methods(bot_token);
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            var result = client.conversationsHistory(r -> r
                    .channel(channel)
                    .limit(1)
            );
            conversationHistory = Optional.ofNullable(result.getMessages());
            String history_text = conversationHistory.toString().split("text=")[1].split(",")[0];
            logger.info("{} messages found in {}", conversationHistory.orElse(emptyList()).size(), channel);
            return history_text;
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
        return "";
    }
}