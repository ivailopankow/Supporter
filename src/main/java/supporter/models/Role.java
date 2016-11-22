package supporter.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Entity
@Table(name = "roles")
public class Role {
    private Integer id;

    private String name;

    private Set<User> users;

    public Role() {
        this.users = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
