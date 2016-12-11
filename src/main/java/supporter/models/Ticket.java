package supporter.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "tickets")
public class Ticket {

    private Long id;
    private String title;
    private String body;
    private User author;
    private Product product;
    private Date date = new Date();
    private Set<Comment> comments;

    public Ticket(String title, String body, User author, Product product) {
        this.setTitle(title);
        this.setBody(body);
        this.setAuthor(author);
        this.setProduct(product);
        this.comments = new HashSet<>();
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
