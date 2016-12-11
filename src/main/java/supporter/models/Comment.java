package supporter.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ivaylo on 11-Dec-16.
 */
@Entity
@Table(name = "comments")
public class Comment {
    private Long id;
    private String content;
    private User author;
    private Ticket ticket;
    private Date date = new Date();

    public Comment(String content, User author, Ticket ticket) {
        this.content = content;
        this.author = author;
        this.ticket = ticket;
    }

    public Comment() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "ticketId")
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
