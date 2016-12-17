package supporter.utils;

/**
 * Created by Ivaylo on 13-Nov-16.
 */
public class ValidationRules {

    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 2;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final int FIRST_NAME_MIN_LENGTH = 2;
    public static final int FIRST_NAME_MAX_LENGTH = 10;
    public static final int LAST_NAME_MIN_LENGTH = 2;
    public static final int LAST_NAME_MAX_LENGTH = 10;

   public static final String USERNAME_ERROR_MESSAGE = "Username size should be in the range [" +
                                                USERNAME_MIN_LENGTH + "..." +  USERNAME_MAX_LENGTH + "]";


    public static final int PRODUCT_TITLE_MIN_LENGTH = 4;
    public static final int PRODUCT_TITLE_MAX_LENGTH = 15;
    public static final String PRODUCT_TITLE_ERROR_MESSAGE = "Product title should be in the range [" +
                             PRODUCT_TITLE_MIN_LENGTH + "..." +  PRODUCT_TITLE_MAX_LENGTH + "]";
    public static final int PRODUCT_CONTENT_MIN_LENGTH = 5;
    public static final int PRODUCT_CONTENT_MAX_LENGTH = 800;
    public static final String PRODUCT_CONTENT_ERROR_MESSAGE = "Product content should be in the range [" +
            PRODUCT_CONTENT_MIN_LENGTH + "..." +  PRODUCT_CONTENT_MAX_LENGTH + "]";
    public static final int CATEGORY_NAME_MIN_LENGTH = 3;
    public static final int CATEGORY_NAME_MAX_LENGTH = 15;
    public static final String CATEGORY_NAME_ERROR_MESSAGE = "Category name should be in the range [" +
            CATEGORY_NAME_MIN_LENGTH + "..." +  CATEGORY_NAME_MAX_LENGTH + "]";
    public static final int TICKET_TITLE_MIN_LENGTH = 4;
    public static final int TICKET_TITLE_MAX_LENGTH = 15;
    public static final String TICKET_TITLE_ERROR_MESSAGE = "Ticket title should be in the range [" +
            TICKET_TITLE_MIN_LENGTH + "..." +  TICKET_TITLE_MAX_LENGTH + "]";
    public static final int TICKET_CONTENT_MIN_LENGTH = 5;
    public static final int TICKET_CONTENT_MAX_LENGTH = 100;
    public static final String TICKET_CONTENT_ERROR_MESSAGE = "Ticket content should be in the range [" +
            TICKET_CONTENT_MIN_LENGTH + "..." +  TICKET_CONTENT_MAX_LENGTH + "]";
}
