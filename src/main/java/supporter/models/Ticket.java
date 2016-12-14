package supporter.models;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "tickets")
public class Ticket implements Comparable<Ticket> {

    private Long id;
    private String title;
    private String body;
    private User author;
    private Product product;
    private Date date = new Date();
    private Set<Comment> comments;
    private boolean isDeleted;

    public Ticket(String title, String body, User author, Product product) {
        this.setTitle(title);
        this.setBody(body);
        this.setAuthor(author);
        this.setProduct(product);
        this.comments = new TreeSet<>();
    }

    public Ticket() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getBody() {
        return body;
    }

    private void setBody(String body) {
        this.body = body;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "authorId")
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

    @ManyToOne()
    @JoinColumn(nullable = false, name = "productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToMany(mappedBy = "ticket")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Transient
    private Set<Comment> getNotDeletedComments() {
        Set<Comment> notDeletedSupportedComments = new TreeSet<>();
        for (Comment comment : comments) {
            if (!comment.isDeleted()) {
                notDeletedSupportedComments.add(comment);
            }
        }
        return notDeletedSupportedComments;
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

    @Override
    public int compareTo(Ticket o) {
        return this.getDate().compareTo(o.getDate());
    }
}
