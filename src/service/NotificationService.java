package service;

import model.Notification;
import model.NotificationStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationService {
    private final NotificationRepository repository;
    private final Map<String, NotificationSender> senders = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void registerSender(String channel, NotificationSender sender) {
        senders.put(channel, sender);
    }

    public Notification createNotification(String content, String recipient) {
        int id = idGenerator.getAndIncrement();
        Notification notification = new Notification(id, content, recipient);
        repository.save(notification);
        return notification;
    }

    public void sendNotification(int id, String channel) {
        Notification notification = repository.findById(id);
        if (notification == null || !senders.containsKey(channel)) {
            return;
        }

        NotificationSender sender = senders.get(channel);
        boolean success = sender.send(notification.getRecipient(), notification.getContent());
        notification.updateStatus(channel, success ? NotificationStatus.SENT : NotificationStatus.FAILED);
        repository.save(notification);
    }

    public List<Notification> findByStatus(String channel, NotificationStatus status) {
        return repository.findByStatus(channel, status);
    }

    public List<Notification> listAllNotifications() {
        return repository.findAll();
    }

    public void deleteNotification(int id) {
        repository.delete(id);
    }

}
