package com.pldfodb.service;

import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    @Autowired SlackSession session;
    private static final String CHANNEL = "yahoo-bot-testing";

    public void sendMessage(String message) {

        //get a channel
        SlackChannel channel = session.findChannelByName(CHANNEL);

        //build a message object
        SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                .withMessage(message)
                .withUnfurl(false)
                .addAttachment(new SlackAttachment())
                .addAttachment(new SlackAttachment())
                .build();

        session.sendMessage(channel, preparedMessage);
    }

    public void sendMessage(SlackPreparedMessage message) {

        SlackChannel channel = session.findChannelByName(CHANNEL);
        session.sendMessage(channel, message);
    }
}
