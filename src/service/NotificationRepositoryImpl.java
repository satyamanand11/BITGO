package service;

import model.Notification;
import model.NotificationStatus;

import java.util.*;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final Map<Integer, Notification>  notificationMap = new HashMap<>();
    private final Map<String, Map<NotificationStatus, Set<Notification>>> statusIndex = new HashMap<>();


    public void save(Notification notification) {
        notificationMap.put(notification.getId(), notification);

        for (Map.Entry<String, NotificationStatus> entry : notification.getChannelStatuses().entrySet()) {
            String channel = entry.getKey();
            NotificationStatus status = entry.getValue();
            statusIndex.computeIfAbsent(channel, k -> new HashMap<>());
            for (NotificationStatus s : NotificationStatus.values()) {
                statusIndex.get(channel).computeIfAbsent(s, k -> new HashSet<>());
                if (s == status) {
                    statusIndex.get(channel).get(s).add(notification);
                } else {
                    statusIndex.get(channel).get(s).remove(notification);
                }
            }
        }
    }

    @Override
    public Notification findById(int id) {
        return notificationMap.get(id);
    }

    @Override
    public List<Notification> findAll() {
        return new ArrayList<>(notificationMap.values());
    }

    @Override
    public void delete(int id) {
        Notification notification = notificationMap.remove(id);
        if (notification != null) {
            for (String channel : notification.getChannelStatuses().keySet()) {
                NotificationStatus status = notification.getStatusForChannel(channel);
                statusIndex.get(channel).get(status).remove(notification);
            }
        }
    }

    public List<Notification> findByStatus(String channel, NotificationStatus status) {
        return new ArrayList<>(statusIndex.getOrDefault(channel, new HashMap<>())
                .getOrDefault(status, new HashSet<>()));
    }
}