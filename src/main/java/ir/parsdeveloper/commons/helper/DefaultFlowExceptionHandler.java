package ir.parsdeveloper.commons.helper;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.utils.StringUtils;
import ir.parsdeveloper.service.api.business.core.BasicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.core.FlowException;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.engine.*;
import org.springframework.webflow.execution.FlowExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author hadi tayebi
 */

public class DefaultFlowExceptionHandler implements FlowExecutionExceptionHandler {

    protected final Log logger = LogFactory.getLog(getClass());
    protected MessageContext messageContext;
    private BasicService basicService;

    public boolean canHandle(FlowExecutionException exception) {
        return true;//  (exception.getCause() instanceof BaseException);
    }

    public void handle(FlowExecutionException exception, RequestControlContext context) {
        Throwable exceptionCause = exception.getCause();
        logger.error(this, exceptionCause);
        try {
            if (exceptionCause instanceof BaseException) {
                messageContext.addError(context, exceptionCause.getMessage());
            } else {
/*
                User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Application currentApplication =null;// getCurrentApplication(context);
                LogException logException = new LogException(exceptionCause,
                        currentApplication,
                        (currentUser == null) ? null : currentUser.getId(),
                        exception.getFlowId(),
                        exception.getStateId());

                logException = (LogException) basicService.saveEntity(logException);
                messageContext.addError(context, "errors.unknown_error");
                messageContext.addError(context, "errors.system_error_code", logException.getId());
*/
            }
            State currentState = (State) context.getCurrentState();

            if (currentState instanceof ActionState) {
                ActionState actionState = (ActionState) currentState;
                TransitionDefinition errorTransition = actionState.getTransition("error");
                if (errorTransition != null) {
                    String targetId = errorTransition.getTargetStateId();
                    State targetState = (State) context.getFlowExecutionContext().getDefinition().getState(targetId);
                    if (targetState != null) {
                        targetState.enter(context);
                    }
                } else {
                    //todo
                }
            } else if (currentState instanceof ViewState) {

                String lastStateName = (String) context.getFlowScope().get(Constants.LAST_STATE_NAME);
                if (StringUtils.hasText(lastStateName)) {
                    State lastState = (State) context.getFlowExecutionContext().getDefinition().getState(lastStateName);
                    lastState.enter(context);

                } else {
                    State finishState = (State) context.getFlowExecutionContext().getDefinition().getState(Constants.FINISH_STATE_NAME);
                    finishState.enter(context);
                }
            }
        } catch (Exception e) {
            logger.error(this.toString(), e);
        }
    }

    public void handleFlowException(String flowId, FlowException exception,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        logger.error(exception);
        try {
            response.sendRedirect(flowId);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Autowired
    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }


}
