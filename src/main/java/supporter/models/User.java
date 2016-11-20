package supporter.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivaylo on 12-Nov-16.
 */

@Entity
@Table(name = "users")
public class User {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Category userCategory;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(length = 60)
    private String passwordHash;

    @Column(length = 100)
    private String fullName;

    @OneToMany(mappedBy = "author")
    private Set<Ticket> tickets = new HashSet<>();
    //endregion

    //region Constructors
    public User(Long id, Category userCategory, String username, String fullName) {
        this.setId(id);
        this.setUserCategory(userCategory);
        this.setUsername(username);
        this.setFullName(fullName);
    }

    public User(Category userCategory, String username, String passwordHash, String fullName){
        this.setUserCategory(userCategory);
        this.setUsername(username);
        this.setPasswordHash(passwordHash);
        this.setFullName(fullName);
    }

    public User() {
    }

    public User(User user) {
        this(user.getUserCategory(), user.getUsername(), user.getPasswordHash(), user.getFullName());
    }
    //endregion

    // TODO: 12-Nov-16 Add validation constraints for fields
    //region Properties
    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Category getUserCategory() {
        return userCategory;
    }

    private void setUserCategory(Category userCategory) {
        this.userCategory = userCategory;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    private void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
    //endregion

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public enum Category {
        DEVELOPER, TESTER, SUPPORTER, CONSUMER
    }
}
