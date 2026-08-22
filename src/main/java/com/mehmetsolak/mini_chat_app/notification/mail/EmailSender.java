package com.mehmetsolak.mini_chat_app.notification.mail;

import com.mehmetsolak.mini_chat_app.common.event.UserCreateEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    @Async
    @EventListener
    public void onUserCreate(UserCreateEvent event) {
        String subject = "Welcome!";
        String htmlBody = """
                <h2>Welcome, %s!</h2>
                <p>Your account created.</p>
                """.formatted(event.firstName() + event.lastName());

        try {
            send(event.email(), subject, htmlBody);
        } catch (MessagingException ignored) {

        }
    }

    private void send(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }
}
