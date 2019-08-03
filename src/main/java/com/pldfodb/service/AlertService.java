package com.pldfodb.service;

import com.pldfodb.controller.model.yahoo.TransactionSourceType;
import com.pldfodb.model.*;
import com.pldfodb.model.event.MatchupStateChangeEvent;
import com.pldfodb.repo.TeamRepository;
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;


@Service
public class AlertService {

    @Autowired private TransactionStateService transactionStateService;
    @Autowired private MatchupStateService matchupStateService;
    @Autowired private SlackService slackService;
    @Autowired private TeamRepository teamRepo;

    private static final Logger LOGGER = Logger.getLogger(AlertService.class.getName());

    @Scheduled(fixedRate = 5000)
    public void transactionAlerts() throws IOException {

        Set<Transaction> newTransactions = transactionStateService.consumeNewTransactions();
        newTransactions.forEach(t -> {

            SlackAttachment transactionAttachment = new SlackAttachment();
            transactionAttachment.setAuthorName(t.getTeam());
            transactionAttachment.addField("Add", format(t.getSourcePlayers(), true), true);
            transactionAttachment.addField("Drop", format(t.getDestinationPlayers(), false), true);
            transactionAttachment.setFooter("<!date^" + t.getDateExecuted().getEpochSecond() + "^{date} {time_secs}|Who knows when>");
            transactionAttachment.setColor("#39138C");

            SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
                    .withMessage("Transaction Alert")
                    .withUnfurl(false)
                    .addAttachment(transactionAttachment)
                    .build();

            LOGGER.info("Sending a message to Slack");
            slackService.sendMessage(preparedMessage, SlackService.ChannelType.MAIN);
        });
    }

    @Scheduled(fixedRate = 5000)
    public void matchupAlerts() throws IOException {

        List<MatchupStateChangeEvent> events = matchupStateService.consumeEvents();
        events.forEach(event -> {

            Matchup matchup;
            SlackPreparedMessage preparedMessage;
            switch (event.getType()) {
                case WIN_PROJECTION:
                    matchup = event.getMatchup();
                    LOGGER.info("Building a message for matchup: " + matchup.toString());
                    MatchupTeam firstMatchupTeam = matchup.getFirst();
                    MatchupTeam secondMatchupTeam = matchup.getSecond();
                    Team firstTeam = teamRepo.getTeam(firstMatchupTeam.getTeamId());
                    Team secondTeam = teamRepo.getTeam(secondMatchupTeam.getTeamId());
                    SlackAttachment transactionAttachment = new SlackAttachment();
                    String message;
                    if (firstMatchupTeam.getWinProbability() == secondMatchupTeam.getWinProbability()) {
                        LOGGER.info("Win probabilities are equal, ignoring until one team takes the lead");
                        return;
                    }
                    else {
                        String winningTeam = firstMatchupTeam.getWinProbability() > secondMatchupTeam.getWinProbability() ? firstTeam.getName() : secondTeam.getName();
                        message = winningTeam + " is now projected to win!\n";
                    }
                    transactionAttachment.setText(message);
                    transactionAttachment.addField(firstTeam.getName(), format(firstMatchupTeam), true);
                    transactionAttachment.addField(secondTeam.getName(), format(secondMatchupTeam), true);
                    transactionAttachment.setColor("#39138C");

                    preparedMessage = new SlackPreparedMessage.Builder()
                            .withMessage("Win Projection Alert")
                            .withUnfurl(false)
                            .addAttachment(transactionAttachment)
                            .build();
                    break;
                default:
                    throw new IllegalArgumentException("Event type " + event.getType() + " is not supported");
            }

            LOGGER.info("Sending a message to Slack");
            slackService.sendMessage(preparedMessage, SlackService.ChannelType.MATCHUPS);
        });
    }

    @Scheduled(cron = "0 0 12 * * TUE")
    public void weeklyTrophyAlert() {


    }

    private String format(MatchupTeam team) {

        StringBuilder builder = new StringBuilder();
        NumberFormat percentFormat = NumberFormat.getPercentInstance();;
        builder.append(percentFormat.format(team.getWinProbability()));
        builder.append(" Chance To Win\n");
//        builder.append(team.getProjected());
//        builder.append(" Proj. Points\n");
        builder.append(team.getScored());
        builder.append(" Points Scored");

        return builder.toString();
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
