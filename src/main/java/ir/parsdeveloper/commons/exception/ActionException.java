package ir.parsdeveloper.commons.exception;

/**
 * @author hadi tayebi
 *         Date: 7/19/13
 */
public class ActionException extends BaseException {
    public ActionException(String message) {
        super(message);
    }


    public ActionException(String message, String... params) {
        super(message, params);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionException(String message, Throwable cause, String... params) {
        super(message, cause, params);
    }

    public ActionException(Throwable cause) {
        super(cause);
    }
}
