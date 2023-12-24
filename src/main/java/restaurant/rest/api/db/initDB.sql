DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS vote;
DROP TABLE IF EXISTS menu_item;
DROP TABLE IF EXISTS restaurant;
DROP TABLE IF EXISTS menu;

CREATE TABLE users
(
    id               INTEGER GENERATED ALWAYS BY DEFAULT AS IDENTITY,
    username         VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOLEAN                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_username_idx ON users (email, username);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id          INTEGER GENERATED ALWAYS BY DEFAULT AS IDENTITY,
    description TEXT      NOT NULL,
    calories    INT       NOT NULL,
    restaurant_name TEXT  NOT NULL,
);

CREATE TABLE vote
(
    id          INTEGER GENERATED ALWAYS BY DEFAULT AS IDENTITY,
    vote_date   DATE DEFAULT now(),
    vote_time   TIME DEFAULT now(),
    user_id     INT NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
);
CREATE INDEX vote_date_index ON vote(vote_date);

CREATE TABLE menu
(
    id          INTEGER GENERATED ALWAYS BY DEFAULT AS IDENTITY,
    date        DATE DEFAULT now(),
    time        TIME DEFAULT now(),
    restaurant_id INT NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
);

CREATE TABLE menu_item
(
    id          INTEGER GENERATED ALWAYS BY DEFAULT AS IDENTITY,
    dish        VARCHAR,
    price       DOUBLE PRECISION,
    menu_id INT NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES restaurant (id) ON DELETE CASCADE,
);