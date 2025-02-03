CREATE TABLE IF NOT EXISTS roles (
                                     id         SERIAL PRIMARY KEY,
                                     rolname    smallint NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
                                     id          BIGSERIAL PRIMARY KEY,
                                     username    VARCHAR(30) NOT NULL,
                                     password    VARCHAR(255) NOT NULL,
                                     email       VARCHAR(50) UNIQUE,
                                     CONSTRAINT email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                                     CONSTRAINT unique_username UNIQUE (username)
);

CREATE INDEX idx_username_hash ON users USING HASH (username);

CREATE TABLE IF NOT EXISTS users_to_roles (
                                    user_id     BIGINT NOT NULL,
                                    role_id     INT NOT NULL,
                                    PRIMARY KEY (user_id, role_id),
                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS friends (
                                    user1_id    BIGSERIAL NOT NULL,
                                    user2_id    BIGSERIAL NOT NULL,
                                    PRIMARY KEY (user1_id, user2_id),
                                    FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CHECK (user1_id <> user2_id)
);

CREATE TABLE IF NOT EXISTS friend_requests (
                                    id            BIGSERIAL PRIMARY KEY,
                                    sender_id     BIGINT NOT NULL,
                                    receiver_id   BIGINT NOT NULL,
                                    status        VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                    request_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CHECK (sender_id <> receiver_id),
                                    CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED'))
);

CREATE TABLE IF NOT EXISTS topics (
                                      id          BIGSERIAL PRIMARY KEY,
                                      name        VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS posts (
                                    id          BIGSERIAL PRIMARY KEY,
                                    user_id     BIGINT NOT NULL,
                                    topic_id    BIGINT NOT NULL,
                                    title       VARCHAR(100) NOT NULL UNIQUE,
                                    content     TEXT NOT NULL,
                                    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE SET NULL ON UPDATE CASCADE
);

