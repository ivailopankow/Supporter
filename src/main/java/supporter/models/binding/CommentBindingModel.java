package supporter.models.binding;

import supporter.utils.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
public class CommentBindingModel {
    @NotNull
    @Size(min = ValidationRules.TICKET_CONTENT_MIN_LENGTH,
            max = ValidationRules.TICKET_CONTENT_MAX_LENGTH,
            message = ValidationRules.TICKET_CONTENT_ERROR_MESSAGE)
    private String content;

    private Long ticketId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
}
