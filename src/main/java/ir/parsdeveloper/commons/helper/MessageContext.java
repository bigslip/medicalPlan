package ir.parsdeveloper.commons.helper;

import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.utils.MessageBundleUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.RequestContext;

import java.io.Serializable;

/**
 * @author hadi tayebi
 */
@Component
public class MessageContext implements Serializable {
    public final static int FATAL_TYPE = 0;
    public final static int ERROR_TYPE = 1;
    public final static int WARNING_TYPE = 2;
    public final static int INFO_TYPE = 3;
    private static Log logger = LogFactory.getLog(MessageContext.class);

    public void addInfo(RequestContext context, String msg, Object... args) {
        addMessage(context, INFO_TYPE, msg, args);
    }


    public void addWarning(RequestContext context, String msg) {
        addMessage(context, WARNING_TYPE, msg);
    }

    public void addError(RequestContext context, BaseException ex) {
        String errorMessage = (ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage();
        addMessage(context, ERROR_TYPE, errorMessage, ex.getParametersAsArray());
    }

    public String addError(RequestContext context, Exception ex) {
        String errorMessage;
        if (ex instanceof NullPointerException) {
            errorMessage = "";
        } else {
            errorMessage = (ex.getCause() == null) ? ex.getMessage() : ex.getCause().getMessage();
        }
        if (ex instanceof BaseException) {
            addMessage(context, ERROR_TYPE, errorMessage, ((BaseException) ex).getParametersAsArray());
        } else {
            addMessage(context, ERROR_TYPE, errorMessage);
        }
        return errorMessage;
    }

    public void addError(RequestContext context, String msg, Object... args) {
        addMessage(context, ERROR_TYPE, msg, args);
    }

    public void addError(RequestContext context, String msg) {
        addMessage(context, ERROR_TYPE, msg);
    }

    public void addMessage(RequestContext context, int msgType, String msg, Object... args) {
        try {
            MessageBuilder messageBuilder = new MessageBuilder().info();
            switch (msgType) {

                case ERROR_TYPE:
                    messageBuilder = new MessageBuilder().error();
                    break;
                case INFO_TYPE:
                    messageBuilder = new MessageBuilder().info();
                    break;
                case WARNING_TYPE:
                    messageBuilder = new MessageBuilder().warning();
                    break;
                case FATAL_TYPE:
                    messageBuilder = new MessageBuilder().fatal();
                    break;
            }
            messageBuilder.code(MessageBundleUtil.getMessage(msg, args));
            context.getMessageContext().addMessage(messageBuilder.build());
        } catch (Exception e) {
            logger.fatal(e.getMessage(), e.getCause());
            addError(context, "error.undefined");
        }
    }


}
