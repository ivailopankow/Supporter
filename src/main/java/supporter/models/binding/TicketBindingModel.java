package supporter.models.binding;

import supporter.utils.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 04-Dec-16.
 */
public class TicketBindingModel {
    @NotNull
    @Size(min = ValidationRules.TICKET_TITLE_MIN_LENGTH,
            max = ValidationRules.TICKET_TITLE_MAX_LENGTH,
            message = ValidationRules.TICKET_TITLE_ERROR_MESSAGE)
    private String title;

    @NotNull
    @Size(min = ValidationRules.TICKET_CONTENT_MIN_LENGTH,
            max = ValidationRules.TICKET_CONTENT_MAX_LENGTH,
            message = ValidationRules.TICKET_CONTENT_ERROR_MESSAGE)
    private String content;

    private int productId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
