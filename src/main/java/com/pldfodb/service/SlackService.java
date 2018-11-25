package com.pldfodb.service;

import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;

public class SlackService {

    @Autowired SlackSession session;

    public void sendMessage(String message)
    {
        //get a channel
        SlackChannel channel = session.findChannelByName("yahoo-bot-testing");

        //build a message object
        SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                .withMessage(message)
                .withUnfurl(false)
                .addAttachment(new SlackAttachment())
                .addAttachment(new SlackAttachment())
                .build();

        session.sendMessage(channel, preparedMessage);
    }
}
