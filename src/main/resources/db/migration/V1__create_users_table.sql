CREATE TABLE users (
       id UUID PRIMARY KEY,
       username VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       email VARCHAR(255) NOT NULL,
       first_name VARCHAR(255) NOT NULL,
       last_name VARCHAR(255) NOT NULL,
       created_at TIMESTAMP,
       updated_at TIMESTAMP,

       CONSTRAINT uk_users_username UNIQUE (username),
       CONSTRAINT uk_users_email UNIQUE (email)
);