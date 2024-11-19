package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification {
    private final int id;
    private final String content;
    private final String recipient;
    private final Map<String, NotificationStatus> channelStatuses;

    public Notification(int id, String content, String recipient) {
        this.id = id;
        this.content = content;
        this.recipient = recipient;
        this.channelStatuses = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getRecipient() {
        return recipient;
    }

    public Map<String, NotificationStatus> getChannelStatuses() {
        return channelStatuses;
    }

    public void updateStatus(String channel, NotificationStatus status) {
        channelStatuses.put(channel, status);
    }

    public NotificationStatus getStatusForChannel(String channel) {
        return channelStatuses.getOrDefault(channel, NotificationStatus.CREATED);
    }

}

