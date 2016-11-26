package supporter.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "tickets")
public class Ticket {

    private Integer id;
    private String title;
    private String body;
    private User author;
    private Product product;
    private Date date = new Date();

    public Ticket(String title, String body, User author, Product product) {
        this.setTitle(title);
        this.setBody(body);
        this.setAuthor(author);
        this.setProduct(product);
    }

    public Ticket() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
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
