package supporter.models.binding;

import supporter.utils.ValidationRules;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
public class CategoryBindingModel {
    @NotNull
    @Size(min = ValidationRules.CATEGORY_NAME_MIN_LENGTH,
            max = ValidationRules.CATEGORY_NAME_MAX_LENGTH,
            message = ValidationRules.CATEGORY_NAME_ERROR_MESSAGE)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
