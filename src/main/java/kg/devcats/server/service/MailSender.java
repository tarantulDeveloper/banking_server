package kg.devcats.server.service;

public interface MailSender {
    void sendRegisterMessage(String to, String name, String token);
}
