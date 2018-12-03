CREATE TABLE matchups (
    week INTEGER NOT NULL,
    playoffs BOOLEAN NOT NULL,
    first_team_id INTEGER NOT NULL,
    first_team_scored NUMERIC NOT NULL,
    first_team_projected NUMERIC NOT NULL,
    first_team_probability NUMERIC NOT NULL,
    second_team_id INTEGER NOT NULL,
    second_team_scored NUMERIC NOT NULL,
    second_team_projected NUMERIC NOT NULL,
    second_team_probability NUMERIC NOT NULL,
    PRIMARY KEY (week, first_team_id, second_team_id)
);

ALTER TABLE teams ADD COLUMN clinched_playoffs BOOLEAN NOT NULL;
ALTER TABLE teams ADD COLUMN manager_id BOOLEAN NOT NULL;
ALTER TABLE teams ADD COLUMN manager_nickname BOOLEAN;