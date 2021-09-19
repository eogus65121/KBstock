package com.ezTstock.slack.common;

import com.ezTstock.config.SlackImpl;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        List<String[]> list = new ArrayList<>();

        try {
            for(int i = 1; i < inputNews.length; i++){
                list.add(inputNews[i].split("\n"));
            }
            System.out.println();

            ChatPostMessageResponse response = Slack.getInstance().methods(token).chatPostMessage(req -> req
                    .channel(channel)
                    .blocks(asBlocks(
                            section(s->s.text(markdownText(inputNews[0]))),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(0)[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(0)[1])))),
                            section(se->se.text(markdownText(list.get(0)[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(list.get(0)[2])))
                            ),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(1)[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(1)[1])))),
                            section(se->se.text(markdownText(list.get(1)[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(list.get(1)[2])))
                            ),
                            divider(),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(2)[0])))),
                            section(s->s.text(plainText(pt->pt.emoji(true).text(list.get(2)[1])))),
                            section(se->se.text(markdownText(list.get(2)[3]))
                                    .accessory(button(b->b.text(plainText(pt->pt.emoji(true).text("자세히 알아보기")))
                                            .url(list.get(2)[2])))
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
