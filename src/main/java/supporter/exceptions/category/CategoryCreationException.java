package supporter.exceptions.category;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
public class CategoryCreationException extends Exception {
    public CategoryCreationException(String categoryNameError) {
        super(categoryNameError);
    }
}
