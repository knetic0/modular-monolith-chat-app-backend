CREATE TABLE messages (
  id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  conversation_id UUID NOT NULL,
  sender_id       UUID NOT NULL,
  content         TEXT,
  message_type    VARCHAR(20) NOT NULL DEFAULT 'TEXT',
  reply_to_id     UUID,
  created_at      TIMESTAMP NOT NULL DEFAULT now(),
  edited_at       TIMESTAMP,
  CONSTRAINT fk_messages_conversation
      FOREIGN KEY (conversation_id) REFERENCES conversations (id)
          ON DELETE CASCADE,
  CONSTRAINT fk_messages_reply_to
      FOREIGN KEY (reply_to_id) REFERENCES messages (id)
          ON DELETE SET NULL
);

CREATE INDEX idx_messages_conversation
    ON messages (conversation_id, created_at DESC);