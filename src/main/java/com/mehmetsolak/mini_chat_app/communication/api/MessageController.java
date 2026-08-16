package com.mehmetsolak.mini_chat_app.communication.api;

import com.mehmetsolak.mini_chat_app.communication.api.dto.request.DirectMessageRequest;
import com.mehmetsolak.mini_chat_app.communication.api.dto.response.DirectMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message.send")
    public void sendDirectMessage(DirectMessageRequest request, Principal principal) {
        DirectMessageResponse resp =
                new DirectMessageResponse(principal.getName(), request.content());

        messagingTemplate.convertAndSendToUser(
                request.receiver(),
                "/queue/messages",
                resp
        );
    }
}
