package supporter.utils;

/**
 * Created by Ivaylo on 25-Nov-16.
 */
public class Const {
    private static final String BINDING_RESULT = "org.springframework.validation.BindingResult.";

    public static final String ROLE_USER_KEY = "ROLE_USER";
    public static final String ROLE_ADMIN_KEY = "ROLE_ADMIN";

    public static final String BINDING_MODEL_REGISTER = "registerForm";
    public static final String BINDING_MODEL_CREATE_PRODUCT = "productForm";
    public static final String BINDING_MODEL_CATEGORY = "categoryForm";
    public static final String BINDING_MODEL_CREATE_TICKET = "ticketForm";
    public static final String BINDING_MODEL_CREATE_COMMENT = "commentForm";
    public static final String BINDING_MODEL_RESULT_REGISTER = BINDING_RESULT + BINDING_MODEL_REGISTER;
    public static final String BINDING_MODEL_RESULT_PRODUCT = BINDING_RESULT + BINDING_MODEL_CREATE_PRODUCT;
    public static final String BINDING_MODEL_RESULT_CATEGORY = BINDING_RESULT + BINDING_MODEL_CATEGORY;
    public static final String BINDING_MODEL_RESULT_TICKET = BINDING_RESULT + BINDING_MODEL_CREATE_TICKET;
    public static final String BINDING_MODEL_RESULT_COMMENT = BINDING_RESULT + BINDING_MODEL_CREATE_COMMENT;

    public static final String USER_VIEW_KEY = "user";
    public static final String NOTIFICATION_MESSAGE_VIEW_KEY = "message";
    public static final String PRODUCTS_VIEW_KEY = "products";
    public static final String CATEGORIES_VIEW_KEY = "categories";
    public static final String PRODUCT_VIEW_KEY = "product";
    public static final String CATEGORY_VIEW_KEY = "category";
    public static final String USERS_VIEW_KEY = "users";
    public static final String ROLES_VIEW_KEY = "roles";
    public static final String TICKET_VIEW_KEY = "ticket";
    public static final String NEW_COMMENT_VIEW_KEY = "newComment";
    public static final String SUPPORTED_PRODUCTS = "subscribedProducts";
    public static final String PRODUCT_USER_RELATION_VIEW_KEY = "userProductRelated";
    public static final String TICKETS_VIEW_KEY = "tickets";
    public static final String LATEST_FIVE_VIEW_KEY = "latestFiveProducts";
    public static final String LATEST_THREE_VIEW_KEY = "latestThreeProducts";
}
