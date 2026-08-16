CREATE TABLE conversation_participants (
   conversation_id      UUID NOT NULL,
   user_id               UUID NOT NULL,
   joined_at             TIMESTAMP NOT NULL DEFAULT now(),
   last_read_message_id  UUID,
   is_muted              BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (conversation_id, user_id),
   CONSTRAINT fk_conversation_participants_conversation
       FOREIGN KEY (conversation_id) REFERENCES conversations (id)
           ON DELETE CASCADE
);

CREATE INDEX idx_conversation_participants_user_id
    ON conversation_participants (user_id);