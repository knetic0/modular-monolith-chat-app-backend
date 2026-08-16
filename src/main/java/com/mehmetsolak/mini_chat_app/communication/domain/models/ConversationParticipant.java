package com.mehmetsolak.mini_chat_app.communication.domain.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversation_participants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationParticipant {
    @EmbeddedId
    private ConversationParticipantId id;

    @MapsId("conversationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private Conversation conversation;

    @Column(name = "user_id", insertable = false, updatable = false)
    private UUID userId;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;

    @Column(name = "last_read_message_id")
    private UUID lastReadMessageId;

    @Column(name = "is_muted", nullable = false)
    @Builder.Default
    private boolean isMuted = false;
}
