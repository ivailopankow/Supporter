package supporter.controllers;

import supporter.utils.NotificationMessage;

/**
 * Created by Ivaylo on 01-Dec-16.
 */
public class BaseController {

    NotificationMessage generateNotificationMessage(String text, NotificationMessage.Type type) {
        switch (type) {
            case ERROR:
                return NotificationMessage.getErrorNotificationMessage(text);
            case INFO:
                return NotificationMessage.getInfoNotificationMessage(text);
            default:
                throw new IllegalArgumentException("Wrong message type");
        }
    }
}
