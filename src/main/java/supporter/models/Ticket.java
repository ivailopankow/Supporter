package supporter.models;

import java.util.Date;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public class Ticket {
    private Long id;
    private String title;
    private String body;
    private User author;
    private Date date = new Date();

    public Ticket(Long id, String title, String body, User author) {
        this.setId(id);
        this.setTitle(title);
        this.setBody(body);
        this.setAuthor(author);
    }

    public Ticket() {
    }

    // TODO: 12-Nov-16 Add validation constraints for fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    private void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    private void setAuthor(User author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", body='" + body + '\'' +
               ", author=" + author +
               ", date=" + date +
               '}';
    }
}
