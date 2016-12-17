package supporter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import supporter.models.Category;
import supporter.models.Product;
import supporter.models.Ticket;
import supporter.services.category.CategoryService;
import supporter.utils.Const;
import supporter.utils.DisplayedMessages;
import supporter.utils.NotificationMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 01-Dec-16.
 */
public class BaseController {

    @Autowired
    private CategoryService categoryService;

    protected void loadCategories(final Model model) {
        List<Category> categories = categoryService.findAll(true);
        model.addAttribute(Const.CATEGORIES_VIEW_KEY, categories);
    }

    protected NotificationMessage generateNotificationMessage(String text, NotificationMessage.Type type) {
        switch (type) {
            case ERROR:
                return NotificationMessage.getErrorNotificationMessage(text);
            case INFO:
                return NotificationMessage.getInfoNotificationMessage(text);
            default:
                throw new IllegalArgumentException("Wrong message type");
        }
    }

    protected void showNonExistingResourceError(RedirectAttributes redirectAttributes) {
        String messageText = DisplayedMessages.NON_EXISTING_RESOURCE;
        NotificationMessage message = generateNotificationMessage(messageText, NotificationMessage.Type.ERROR);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
    }

    protected void showDeletedEntityError(RedirectAttributes redirectAttributes) {
        String msg = DisplayedMessages.DELETED_ENTITY;
        NotificationMessage message = generateNotificationMessage(msg, NotificationMessage.Type.ERROR);
        redirectAttributes.addFlashAttribute(Const.NOTIFICATION_MESSAGE_VIEW_KEY, message);
    }

    List<Product> getSortedProducts(Collection<Product> products) {
        List<Product> result = new ArrayList<>(products.size());
        result.addAll(products);
        result = result.stream()
                .sorted()
                .collect(Collectors.toList());
        return result;
    }

    List<Ticket> getSortedTickets(Collection<Ticket> tickets) {

        List<Ticket> result = new ArrayList<>(tickets.size());
        result.addAll(tickets);
        result = result.stream()
                .sorted()
                .collect(Collectors.toList());
        return result;
    }
}
