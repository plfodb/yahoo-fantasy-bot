package com.pldfodb.service;

import com.google.common.collect.ImmutableList;
import com.pldfodb.controller.model.yahoo.MatchupResource;
import com.pldfodb.model.Matchup;
import com.pldfodb.model.Team;
import com.pldfodb.model.event.MatchupStateChangeEvent;
import com.pldfodb.model.event.WinProjectionEvent;
import com.pldfodb.repo.MatchupRepository;
import com.pldfodb.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MatchupStateService extends YahooOAuthService {

    @Autowired private MatchupRepository matchupRepo;
    @Autowired private TeamRepository teamRepo;

    private Set<Matchup> matchups;
    private List<MatchupStateChangeEvent> events = new ArrayList<>();

    private static final Logger LOGGER = Logger.getLogger(MatchupStateService.class.getName());

    private void initialize() throws IOException {

        setAuthentication();
        if (matchups == null) {
            matchups = new HashSet<>();
            matchups.addAll(matchupRepo.getLatestMatchups());
        }
    }

    @Scheduled(fixedRate = 5000)
    public void updateMatchups() throws IOException {

        if (yahooClient == null) {
            LOGGER.warning("Yahoo client is not initialized. Do you need to GET /yahoo/login and then restart?");
            return;
        }

        initialize();

        List<MatchupResource> updatedMatchupResources = yahooClient.getMatchups();
        Set<Matchup> updatedMatchups = updatedMatchupResources.stream().map(MatchupResource::getMatchup).collect(Collectors.toSet());
        List<Team> teams = updatedMatchupResources.stream().map(MatchupResource::getTeams).flatMap(t -> t.stream()).collect(Collectors.toList());
        teamRepo.saveTeams(teams);
        if (!matchups.equals(updatedMatchups)) {

            LOGGER.info("Matchups changed");

            Iterator<Matchup> updatedIt = updatedMatchups.iterator();
            Iterator<Matchup> it = matchups.iterator();
            while (updatedIt.hasNext()) {
                Matchup existing = it.next();
                Matchup updated = updatedIt.next();

                // new week, clear cache
                if (existing.getWeek() != updated.getWeek())
                   break;

                double oldProjectionDelta = existing.getFirst().getProjected() - existing.getSecond().getProjected();
                double updatedProjectionDelta = updated.getFirst().getProjected() - updated.getSecond().getProjected();

                // if the deltas have different signs there was a lead change in win probability
                if ((oldProjectionDelta > 0) != (updatedProjectionDelta > 0))
                    events.add(new WinProjectionEvent(updated));
            }
            matchups.clear();
            matchups.addAll(updatedMatchups);
        }
    }

    public List<MatchupStateChangeEvent> consumeEvents() {

        List<MatchupStateChangeEvent> eventsCopy = ImmutableList.copyOf(events);
        matchupRepo.saveMatchups(events.stream().map(MatchupStateChangeEvent::getMatchup).collect(Collectors.toSet()));
        int eventCount = events.size();
        events.clear();
        if (eventCount > 0)
            LOGGER.info(String.format("Sent %d matchup state change events", eventCount));

        return eventsCopy;
    }
}
