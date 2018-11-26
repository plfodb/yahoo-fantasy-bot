CREATE TABLE tokens (
    token TEXT NOT NULL
);

CREATE TABLE teams (
    team_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE players (
    player_id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    pos TEXT NOT NULL
);

CREATE TABLE transactions (
    transaction_id INTEGER PRIMARY KEY,
    type TEXT NOT NULL,
    date_executed TIMESTAMP NOT NULL,
    source_team TEXT,
    dest_team TEXT
);

CREATE TABLE player_transactions (
    transaction_id INTEGER NOT NULL,
    player_id BIGINT NOT NULL,
    source TEXT NOT NULL,
    destination TEXT NOT NULL,
    PRIMARY KEY (transaction_id, player_id)
);

CREATE TABLE trophies (
    trophy_id VARCHAR(50) PRIMARY KEY,
    title TEXT NOT NULL,
    grade INTEGER NOT NULL,
    description TEXT
);

CREATE TABLE trophy_owners (
    trophy_id VARCHAR(50),
    team_id INTEGER,
    date_awarded TIMESTAMP NOT NULL,
    PRIMARY KEY (trophy_id, team_id)
);