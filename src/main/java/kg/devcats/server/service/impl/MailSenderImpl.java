package kg.devcats.server.service.impl;

import kg.devcats.server.service.MailSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {
    final JavaMailSender mailSender;

    @Value("${server.host}")
    String host;
    @Value("${spring.mail.username}")
    String fromEmail;


    @Override
    @Async
    public void sendRegisterMessage(String to, String name, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setSubject("Подтверждение почты");
            message.setTo(to);
            message.setText(MailUtils.getRegistarionMessage(name, host, token));
            mailSender.send(message);
        } catch (Exception e) {
            throw new MailSendException(e.getMessage());
        }
    }

    private static class MailUtils {
        public static String getRegistarionMessage(String name, String host, String token) {
            return "Привет, " + name + "\n\nВаш аккаунт на платформе " +
                    "Software Updater Banking создан! Пожалуйста, " +
                    "пройдите по ссылке, чтобы активировать аккаунт: \n\n"
                    + getVerificationUrl(host, token);
        }
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/api/auth/activate?token=" + token;
    }
}
