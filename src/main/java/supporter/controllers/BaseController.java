package supporter.controllers;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
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

    public void showNonExistingResourceError(RedirectAttributes redirectAttributes) {
        String messageText = DisplayedMessages.NON_EXISTING_RESOURCE;
        NotificationMessage message = generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
    }
}
