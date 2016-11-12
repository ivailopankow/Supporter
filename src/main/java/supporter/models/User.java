package supporter.models;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private String fullName;
    private Set<Ticket> tickets = new HashSet<>();
    private Category userCategory;

    public User(Long id, Category userCategory, String username, String fullName) {
        this.setId(id);
        this.setUserCategory(userCategory);
        this.setUsername(username);
        this.setFullName(fullName);
    }

    private User() {
    }

    // TODO: 12-Nov-16 Add validation constraints for fields
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
        DEVELOPER, SUPPORTER, CONSUMER
    }
}
