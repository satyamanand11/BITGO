import model.Notification;
import model.NotificationStatus;
import service.*;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        NotificationRepository repository = new NotificationRepositoryImpl();
        NotificationService notificationService = new NotificationService(repository);

        notificationService.registerSender("email", new EmailSender());

        Notification notification1 = notificationService.createNotification("BTC", "user@example.com");
        Notification notification2 = notificationService.createNotification("BITGO", "user@mgail.com");

        notificationService.sendNotification(notification1.getId(), "email");
        notificationService.sendNotification(notification2.getId(), "email");
        notificationService.sendNotification(notification1.getId(), "sms");

        System.out.println("Notifications with SENT status for Email:");
        List<Notification> emailSentNotifications = notificationService.findByStatus("email", NotificationStatus.SENT);
        for (Notification n : emailSentNotifications) {
            System.out.println("ID: " + n.getId() + ", Content: " + n.getContent());
        }

        System.out.println("\nNotifications with OUTSTANDING status for SMS:");
        List<Notification> smsOutstandingNotifications = notificationService.findByStatus("sms", NotificationStatus.CREATED);
        for (Notification n : smsOutstandingNotifications) {
            System.out.println("ID: " + n.getId() + ", Content: " + n.getContent());
        }

        System.out.println("\nAll Notifications:");
        for (Notification n : notificationService.listAllNotifications()) {
            System.out.println("ID: " + n.getId() +
                    ", Content: " + n.getContent() +
                    ", Email Status: " + n.getStatusForChannel("email") +
                    ", SMS Status: " + n.getStatusForChannel("sms"));
        }
    }

}