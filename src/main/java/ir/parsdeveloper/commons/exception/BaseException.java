package ir.parsdeveloper.commons.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author hadi tayebi
 *         Date: 7/19/13
 */
public class BaseException extends Exception {
    protected List<String> parameters = new ArrayList<String>();

    public BaseException(BaseException e) {
        super(e.getMessage());
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, String... params) {
        // this(message);
        addParameters(params);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message, Throwable cause, String... params) {
        this(message, cause);
        addParameters(params);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public void init(Throwable cause) {
        super.initCause(cause);
    }

    public void addParameters(String... params) {

        Collections.addAll(parameters, params);

    }

    public Object[] getParametersAsArray() {
        return parameters.toArray();
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    /*  @Override
    public String getMessage() {

        return (getCause()!=null)?getCause().getMessage():getMessage();
    }*/
}
