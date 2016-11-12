package supporter.model;

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

    private Ticket(Long id, String title, String body, User author) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    private Ticket() {
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private String getBody() {
        return body;
    }

    private void setBody(String body) {
        this.body = body;
    }

    private User getAuthor() {
        return author;
    }

    private void setAuthor(User author) {
        this.author = author;
    }

    private Date getDate() {
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
