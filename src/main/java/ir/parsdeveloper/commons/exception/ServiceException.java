package ir.parsdeveloper.commons.exception;

/**
 * @author hadi tayebi
 */
public class ServiceException extends BaseException {
    public ServiceException(Throwable e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }

}
