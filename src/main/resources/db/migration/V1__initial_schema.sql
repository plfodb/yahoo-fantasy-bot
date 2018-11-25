CREATE TABLE tokens (
    token TEXT NOT NULL
);

CREATE TABLE teams (
    team_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL
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