package com.pldfodb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pldfodb.controller.model.slack.UrlVerificationEvent;
import com.pldfodb.service.AlertService;
import com.pldfodb.service.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/slack")
public class SlackController {

    @Autowired private SlackService slackService;
    @Autowired private AlertService alertService;

    @RequestMapping(value = "/events", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody String events(@RequestBody UrlVerificationEvent event) {

        return event.getChallenge();
    }

    @PostMapping(value = "/messages")
    public @ResponseBody void sendMessage(@RequestBody String message) {

        slackService.sendMessage(message, SlackService.ChannelType.MAIN);
    }

    @PostMapping("/alerts/transactions")
    public @ResponseBody void sendTransactionAlerts() throws IOException {

        alertService.transactionAlerts();
    }
}
