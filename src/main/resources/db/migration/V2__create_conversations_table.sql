CREATE TABLE conversations (
   id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   is_group       BOOLEAN NOT NULL DEFAULT FALSE,
   name           VARCHAR(100),
   created_at     TIMESTAMP NOT NULL DEFAULT now(),
   last_message_at TIMESTAMP
);