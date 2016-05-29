package ir.parsdeveloper.commons;

import java.io.Serializable;

/**
 * @author hadi tayebi
 */
public final class Constants implements Serializable {

    public static final String APPLICATION_URL = "APPLICATION_URL";

    public static final String PRODUCT_PROFILE = "PRODUCT";
    public static final String TEST_PROFILE = "TEST";


    public static final String WEB_SERVICE_LOCATION_URL = "/ws";


    public static final String ACCEPT_IMAGE_MIME_TYPES = "image/gif image/png image/jpg image/jpeg";
    public final static Integer MAX_UPLOAD_SIZE = 512 * 1024;//512 KB

    public static final String NUMBER_REGEX = "\\d+";

    public static final String UTF8_ENCODING = "UTF-8";


    public static final String BLANK_SPACE = " ";
    public static final String EMPTY_STRING = "";

    public final static Integer DEFAULT_PAGE_SIZE = 10;
    public final static String ENTITY_LIST = "List";

    public static final String FORM_OBJECT_VALUE = "formObjectValue";
    //public final static String SELECTED_ROW_ID = "SELECTED_ROW_ID";
    public final static String SELECTED_ROWS = "_selected_rows";
    public final static String FORM_OBJECT_NAME = "formObjectName";
    public final static String PAGE_MODE = "pageMode";
    public final static String CURRENT_USER_SESSION_KEY = "CURRENT_USER";
    public final static String CURRENT_APPLICATION_SESSION_KEY = "CURRENT_APPLICATION";
    public final static String MENU_LIST_KEY = "MENU_LIST";
    public final static String SIDEBAR_MENU_LIST_KEY = "SIDEBAR_MENU_LIST";
    public final static String UPDATE_PAGE_MODE = "update";
    public final static String VIEW_PAGE_MODE = "view";
    public final static String LIST_VIEW_SUFFIX = "ListView";
    public final static String SINGLE_VIEW_SUFFIX = "SingleView";
    public final static String LAST_STATE_NAME = "lastStateName";
    public final static String FINISH_STATE_NAME = "finish";
    public final static String LAST_QUERY_STRING = "LAST_QUERY_STRING";


    public final static String DELIMITER = "#";

    public final static String APPLICATION_CONFIG = "config/application.properties";
    public final static String PACKAGE_TO_SCAN = "ir.parsdeveloper.model.entity";
    public static final java.lang.String PRIMITIVE_CACHE_NAME = "primitiveCache";


    public static final String CAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    public static final String ANONYMOUS_USER_PRINCIPAL = "anonymousUser";

    public static final String DEFAULT_PASSWORD = "123";



    //------------------------Role-----------------------
    public static final Long PLAN_ROLE = 2L;
    public static final Long RECEPTION_ROLE = 3L;

}
