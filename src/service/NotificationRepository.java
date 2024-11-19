package service;

import model.Notification;
import model.NotificationStatus;

import java.util.List;

public interface NotificationRepository {

    void save(Notification notification);
    Notification findById(int id);
    List<Notification> findAll();
    List<Notification> findByStatus(String channel, NotificationStatus status);
    void delete(int id);
}
