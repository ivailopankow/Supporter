package supporter.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "tickets")
public class Ticket {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Lob @Column(nullable = false)
    private String body;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User author;

    @Column(nullable = false)
    private Date date = new Date();
    //endregion

    //region Constructors
    public Ticket(Long id, String title, String body, User author) {
        this.setId(id);
        this.setTitle(title);
        this.setBody(body);
        this.setAuthor(author);
    }

    public Ticket() {
    }
    //endregion

    // TODO: 12-Nov-16 Add validation constraints for fields
    //region Properties
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
    //endregion

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
