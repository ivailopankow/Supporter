package supporter.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
    private Integer id;
    private String title;
    private String content;
    private User producer;
    private Set<User> supportedUsers;
    private Set<Ticket> tickets;
    private Date date = new Date();
    private Category category;
    private boolean isDeleted;

    public Product() {
    }

    public Product(String title, String content, User producer, Category category) {
        this.title = title;
        this.content = content;
        this.producer = producer;
        this.category = category;
        this.tickets = new TreeSet<>();
        this.supportedUsers = new HashSet<>();
        this.isDeleted = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(columnDefinition = "text", nullable = false)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "producerId")
    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
        this.producer = producer;
    }

    @OneToMany(mappedBy = "product")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @ManyToMany(mappedBy = "supportedProducts")
    public Set<User> getSupportedUsers() {
        return supportedUsers;
    }

    public void setSupportedUsers(Set<User> supportedUsers) {
        this.supportedUsers = supportedUsers;
    }

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDate() {
        return date;
    }

    private void setDate(Date date) {
        this.date = date;
    }

    @Transient
    public String getSummary(){
        int endIndex = getContent().length() / 2;
        return getContent().substring(0, endIndex) + "...";
    }

    @Transient
    public Set<Ticket> getNotDeletedTickets() {
        Set<Ticket> notDeletedSupportedTickets = new TreeSet<>();
        for (Ticket ticket : tickets) {
            if (!ticket.isDeleted()) {
                notDeletedSupportedTickets.add(ticket);
            }
        }
        return notDeletedSupportedTickets;
    }

    @Override
    public int compareTo(Product o) {
        return this.getDate().compareTo(o.getDate());
    }
}
