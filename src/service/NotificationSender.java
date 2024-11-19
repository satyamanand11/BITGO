package service;

public interface NotificationSender {
    boolean send(String receiver, String content);
}
