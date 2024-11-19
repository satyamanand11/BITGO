package service;

public class EmailSender implements NotificationSender{

    @Override
    public boolean send(String recipient, String content) {
        System.out.println("Sending Email to: " + recipient + " with content: " + content);
        return true;
    }
}
