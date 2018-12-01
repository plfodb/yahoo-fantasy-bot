## Dependencies

- Java 8
- Maven
- Docker
- Docker Compose
- Postgres (Optional)

## Build

There are 3 files to copy before the bot will work properly:

1. `yahoo.properties.template`
2. `slack.properties.template`
3. `jwt.properties.template`

Copy each of the above and give them the following names: 

1. `yahoo.properties`
2. `slack.properties`
3. `jwt.properties`

Ask Bryan to send you the creds. Fill in the creds in the new `.properties` files. Then run:

`mvn clean install`

### Build Docker Image

`docker-compose build`

## Run

The bot can be run as a standalone app or part of the provided Docker Compose environment.

### Standalone

To run as a standalone app, first create a postgres database and configure the values in `db.properties` appropriately. You can easily do this by running the command 

`docker run -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:9.5`

Then run:

`java -jar target/yahoo-fantasy-bot-0.1.0-SNAPSHOT.jar`

### Docker Compose

`docker-compose up -d`

## Authentication

### Yahoo Fantasy Bot

Based on the secret in `jwt.properties`, encode a JWT with the HS512 algorithm and send the following header with each request:

`Authorization: Bearer <token>`

Example JWT payload:

`{"sub": "btrujillo","name": "Bryan Trujillo","iat": 1516239022,"exp": 1514793600}`

### Yahoo

#### Endpoints utitlizing `YahooClient`

It's complicated because Yahoo will not redirect to localhost after an OAuth login. Ask Bryan.

#### Scheduled tasks run by `YahooOAuthService`

The database needs to be seeded with a valid OAuth token. This can be done through the endpoint `POST /tokens`. Ask Bryan.

## Current Endpoints

- `/yahoo/league` - returns the league name
- `/yahoo/transactions` - retrieves the last 20 transactions

## Alerts

- Transactions
- No-op Trophy (some of the domain is done)

## TODO

- Alerts
    - Player status
    - Trophies
    - Playoff clinches
    - Playoff wins/losses/close games
