package com.ezTstock.slack.function;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.button;

public class SlackSendMessage {
    public static void slackSendMessage(String text){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");

        try {
            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .text(text)
                    );
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

    public static void slackSendMessage(String[] inputNews){
        SlackImpl slack_json = new SlackImpl();
        String token = slack_json.readJ("bot_token");
        String channel = slack_json.readJ("channel");

        try {
            String[] News1 = inputNews[1].split("\n");
            String[] News2 = inputNews[2].split("\n");
            String[] News3 = inputNews[3].split("\n");

            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .blocks(asBlocks(
                            section(s->s.text(markdownText(inputNews[0]))),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News1[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News1[1])))),
                            section(se->se.text(markdownText(News1[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(News1[2])))
                            ),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News2[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News2[1])))),
                            section(se->se.text(markdownText(News2[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(News2[2])))
                            ),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News3[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(News3[1])))),
                            section(se->se.text(markdownText(News3[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(News3[2])))
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

    /*
    public static void main(String[] args) throws Exception {
        slackSendMessage("test");
    }

    */


}
