## Build

Substitute the dummy values in `src/main/resources/yahoo.properties.template` for real values, rename to `yahoo.properties`, then:
`mvn clean install`

## Run

`cd target`
`java -jar zip-service-0.1.0-SNAPSHOT.jar`

## Current Endpoints

`/yahoo/league` - returns the league name
`/yahoo/transactions` - retrieves the last 20 transactions

## TODO

1. JWT or OAuth authentication so we can open the service to the internet
2. OAuth refresh token functionality so user authorization isn't needed every hour
4. Some form of scheduling/polling, for notifications on transactions, playoff spot clinches, etc.
3. Slack integration
