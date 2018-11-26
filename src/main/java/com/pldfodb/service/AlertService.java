package com.pldfodb.service;

import com.pldfodb.controller.model.yahoo.TransactionSourceType;
import com.pldfodb.model.Player;
import com.pldfodb.model.PlayerTransaction;
import com.pldfodb.model.Transaction;
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Service
public class AlertService {

    @Autowired private TransactionStateService transactionStateService;
    @Autowired private SlackService slackService;

    @Scheduled(fixedRate = 5000)
    public void transactionAlerts() throws IOException {

        Set<Transaction> newTransactions = transactionStateService.consumeNewTransactions();
        newTransactions.forEach(t -> {

            SlackAttachment transactionAttachment = new SlackAttachment();
            transactionAttachment.setPretext("Transaction Alert");
            transactionAttachment.setAuthorName(t.getTeam());
            transactionAttachment.addField("Add", format(t.getSourcePlayers(), true), true);
            transactionAttachment.addField("Drop", format(t.getDestinationPlayers(), false), true);
            transactionAttachment.setFooter("<!date^" + t.getDateExecuted().getEpochSecond() + "^{date} {time_secs}|Who knows when>");
            transactionAttachment.setColor("#39138C");

            SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                    .withUnfurl(false)
                    .addAttachment(transactionAttachment)
                    .build();

            slackService.sendMessage(preparedMessage);
        });
    }

    @Scheduled(cron = "0 0 12 * * TUE")
    public void weeklyTrophyAlert() {


    }

    private String format(Map<Player, PlayerTransaction> players, boolean source) {

        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<Player, PlayerTransaction>> it = players.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<Player, PlayerTransaction> player = it.next();
            TransactionSourceType sourceOrDest = source ? player.getValue().getSource() : player.getValue().getDestination();
            builder.append(String.format("%s %s (%s)", player.getKey().getName(), player.getKey().getPosition(), sourceOrDest));
            builder.append("\n");
        }
        return builder.toString();
    }
}
