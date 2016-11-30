package supporter.models;

import javax.persistence.*;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
@Entity
@Table(name = "products")
public class Product {
    private Integer id;
    private String title;
    private String content;
    private User producer;
    private Category category;

    public Product() {
    }

    public Product(String title, String content, User producer) {
        this.title = title;
        this.content = content;
        this.producer = producer;
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

    @ManyToOne()
    @JoinColumn(nullable = false, name = "categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Transient
    public String getSummary(){
        int endIndex = getContent().length() / 2;
        return getContent().substring(0, endIndex) + "...";
    }
}
