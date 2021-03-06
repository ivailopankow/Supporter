package supporter.models;


import supporter.utils.Const;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "users")
public class User {

    private Integer id;

    private String email;

    private String fullName;

    private String password;

    private Set<Role> roles;

    private Set<Product> products;

    private Set<Product> supportedProducts;

    private Set<Ticket> tickets;

    private Set<Comment> comments;

    public User(String email, String fullName, String password) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;

        this.roles = new HashSet<>();
        this.products = new HashSet<>();
        this.supportedProducts = new HashSet<>();
        this.tickets = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public User() {    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "fullName", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password", length = 60, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(mappedBy = "producer")
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @OneToMany(mappedBy = "author")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscriptions")
    public Set<Product> getSupportedProducts() {
        return supportedProducts;
    }

    public void setSupportedProducts(Set<Product> supportedProducts) {
        this.supportedProducts = supportedProducts;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @OneToMany(mappedBy = "author")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @Transient
    public boolean isAdmin(){
        return this.getRoles().stream()
                .anyMatch( role -> role.getName().equals(Const.ROLE_ADMIN_KEY));
    }

    @Transient
    public boolean isProducer(Product product) {
        return Objects.equals(this.getId(), product.getProducer().getId());
    }

    @Transient
    private Set<Product> notDeletedProducts(Set<Product> products) {
        Set<Product> notDeletedSupportedProducts = new HashSet<>();
        for (Product product : products) {
            if (!product.isDeleted()) {
                notDeletedSupportedProducts.add(product);
            }
        }
        return notDeletedSupportedProducts;
    }

    @Transient
    public Set<Product> getNotDeletedSupportedProducts() {
        return notDeletedProducts(supportedProducts);
    }
    @Transient
    public Set<Product> getNotDeletedProducts() {
        return notDeletedProducts(products);
    }


    @Transient
    public Set<Ticket> getNotDeletedTickets(Set<Ticket> tickets) {
        Set<Ticket> notDeletedSupportedTickets = new TreeSet<>();
        for (Ticket ticket : tickets) {
            if (!ticket.isDeleted()) {
                notDeletedSupportedTickets.add(ticket);
            }
        }
        return notDeletedSupportedTickets;
    }

    @Transient
    public Set<Comment> getNotDeletedComments(Set<Comment> comments) {
        Set<Comment> notDeletedSupportedComments = new TreeSet<>();
        for (Comment comment : comments) {
            if (!comment.isDeleted()) {
                notDeletedSupportedComments.add(comment);
            }
        }
        return notDeletedSupportedComments;
    }
}
