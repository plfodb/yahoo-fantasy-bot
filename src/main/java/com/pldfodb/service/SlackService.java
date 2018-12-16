package com.pldfodb.service;

import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SlackService {

    @Autowired SlackSession session;
    @Autowired @Qualifier("MAIN_CHANNEL") SlackChannel mainChannel;
    @Autowired @Qualifier("MATCHUPS_CHANNEL") SlackChannel matchupsChannel;

    public enum ChannelType {
        MAIN,
        MATCHUPS
    }

    public void sendMessage(String message, ChannelType channelType) {

        SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                .withMessage(message)
                .withUnfurl(false)
                .addAttachment(new SlackAttachment())
                .addAttachment(new SlackAttachment())
                .build();

        sendMessage(preparedMessage, channelType);
    }

    public void sendMessage(SlackPreparedMessage message, ChannelType channelType) {

        SlackChannel channel;
        switch (channelType) {
            case MAIN:
                channel = mainChannel;
                break;
            case MATCHUPS:
                channel = matchupsChannel;
                break;
            default:
                throw new IllegalArgumentException("Channel type " + channelType + " is not supported");
        }

        session.sendMessage(channel, message);
    }
}
