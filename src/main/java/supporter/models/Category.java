package supporter.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ivaylo on 29-Nov-16.
 */
@Entity
@Table(name = "categories")
public class Category {

    private Integer id;
    private String name;
    private Set<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.products = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category")
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
