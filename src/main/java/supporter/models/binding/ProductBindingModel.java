package supporter.models.binding;

import supporter.utils.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public class ProductBindingModel {

    @NotNull
    @Size(min = ValidationRules.PRODUCT_TITLE_MIN_LENGTH,
            max = ValidationRules.PRODUCT_TITLE_MAX_LENGTH,
            message = ValidationRules.PRODUCT_TITLE_ERROR_MESSAGE)
    private String title;

    @NotNull
    @Size(min = ValidationRules.PRODUCT_CONTENT_MIN_LENGTH,
            max = ValidationRules.PRODUCT_CONTENT_MAX_LENGTH,
            message = ValidationRules.PRODUCT_CONTENT_ERROR_MESSAGE)
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
