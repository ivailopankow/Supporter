package supporter.utils;

/**
 * Created by Ivaylo on 01-Dec-16.
 */
public class NotificationMessage {
    private String text;
    private Type type;

    private NotificationMessage(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public static NotificationMessage getErrorNotificationMessage(String text) {
        return new NotificationMessage(text, Type.ERROR);
    }

    public static NotificationMessage getInfoNotificationMessage(String text) {
        return new NotificationMessage(text, Type.INFO);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isError() {
        return this.type == Type.ERROR;
    }

    public enum Type {
        ERROR,
        INFO
    }
}
