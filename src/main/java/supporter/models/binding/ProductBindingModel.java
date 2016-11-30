package supporter.models.binding;

import javax.validation.constraints.NotNull;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public class ProductBindingModel {
    @NotNull
    private String title;
    @NotNull
    private String content;
    private int categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
