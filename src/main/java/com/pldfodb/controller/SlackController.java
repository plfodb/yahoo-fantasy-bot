package com.pldfodb.controller;

import com.pldfodb.controller.model.slack.UrlVerificationEvent;
import com.pldfodb.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/slack")
public class SlackController {

    @Autowired private SlackService slackService;

    @RequestMapping(value = "/events", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String events(@RequestBody UrlVerificationEvent event) {
        return event.getChallenge();
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public void events(@RequestBody String message) {
        slackService.sendMessage(message);
    }
}
