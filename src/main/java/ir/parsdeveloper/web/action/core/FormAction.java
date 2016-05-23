package ir.parsdeveloper.web.action.core;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.helper.DispatchMethodInvoker;
import ir.parsdeveloper.commons.helper.MessageContext;
import ir.parsdeveloper.model.entity.core.Application;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.core.collection.ParameterMap;
import org.springframework.webflow.core.collection.SharedAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.ScopeType;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

/**
 * @author hadi tayebi
 */
@SuppressWarnings("unchecked")
abstract class FormAction<T extends BaseModel> extends AbstractAction implements Serializable {

    public final static String ENTITY_LIST_VIEW = "entityListView";
    public final static String ENTITY_SINGLE_VIEW = "entitySingleView";
    public final static String ERROR_SINGLE_VIEW = "errorView";
    public final static String SelectedRow = "selectedRow";
    public final static String ERRORS_PREFIX = BindingResult.MODEL_KEY_PREFIX;

    protected MessageContext messageContext;
    protected DaoService daoService;

    private DispatchMethodInvoker methodInvoker;
    private Class<? extends T> formObjectClass;
    private String formObjectName;
    private ScopeType formErrorsScope = ScopeType.FLASH;

    FormAction(Class<? extends T> formObjectClass) {
        setFormObjectClass(formObjectClass);
    }

    @PostConstruct
    public void initAction() {
        methodInvoker = new DispatchMethodInvoker(this, RequestContext.class);
    }

    protected void putFormObject(RequestContext context, BaseModel entity) {
        putInFlowScope(context, getFormObjectName(), entity);
    }

    protected Class<? extends T> getFormObjectClass() {
        return formObjectClass;
    }

    protected void setFormObjectClass(Class<? extends T> formObjectClass) {
        this.formObjectClass = formObjectClass;
        if (formObjectClass != null) {
            setFormObjectName(ClassUtils.getShortNameAsProperty(formObjectClass));
        }
    }

    protected String getFormObjectName() {
        return formObjectName;
    }

    private void setFormObjectName(String formObjectName) {
        this.formObjectName = formObjectName;
    }

    protected String getPageMode(RequestContext context) {
        return (String) getFromFlowScope(context, Constants.PAGE_MODE);
    }

    protected ScopeType getFormErrorsScope() {
        return formErrorsScope;
    }

    protected void putFormErrors(RequestContext context, Errors errors) {
        formErrorsScope.getScope(context).put(ERRORS_PREFIX + errors.getObjectName(), errors);
    }

    protected Map getRequestParametersAsMap(RequestContext context) {
        return context.getRequestParameters().asMap();
    }

    protected void putInRequestScope(RequestContext context, String key, Object value) {
        context.getRequestScope().put(key, value);
    }

    protected void putInFlowScope(RequestContext context, String key, Object value) {
        context.getFlowScope().put(key, value);
    }

    protected void putInFlashScope(RequestContext context, String key, Object value) {
        context.getFlashScope().put(key, value);
    }

    protected Object getFromFlashScope(RequestContext context, String key) {
        return context.getFlashScope().get(key);
    }

    protected void putInSessionScope(RequestContext context, String key, Object value) {
        getUserSession(context).put(key, value);
    }

    protected Object getFromSessionScope(RequestContext context, String key) {
        return getUserSession(context).get(key);
    }

    protected SharedAttributeMap getUserSession(RequestContext context) {
        return context.getExternalContext().getSessionMap();
    }

    protected HttpServletResponse getNativeResponse(RequestContext context) {
        return (HttpServletResponse) context.getExternalContext().getNativeResponse();
    }

    protected HttpServletRequest getNativeRequest(RequestContext context) {
        return (HttpServletRequest) context.getExternalContext().getNativeRequest();
    }

    protected HttpSession getHttpSession(RequestContext context) {
        return getNativeRequest(context).getSession();
    }

    protected void putInApplicationScope(RequestContext context, String key, Object value) {
        context.getExternalContext().getApplicationMap().put(key, value);
    }

    protected Object getFromFlowScope(RequestContext context, String key) {
        return context.getFlowScope().get(key);
    }

    protected Object getFromRequestScope(RequestContext context, String key) {
        return context.getRequestScope().get(key);
    }

    protected T getFormObject(RequestContext context) throws ActionException {
        T formObject = (T) getFromFlowScope(context, getFormObjectName());
        return (formObject == null) ? initFormObject(context) : formObject;
    }

    protected void resetForm(RequestContext context) throws ActionException {
        initFormObject(context);
    }

    protected T initFormObject(RequestContext context) {
        T formObject = createFormObject(context);
        putFormObject(context, formObject);
        putInFlowScope(context, Constants.FORM_OBJECT_VALUE, formObject);
        putInFlowScope(context, Constants.FORM_OBJECT_NAME, getFormObjectName());
        return formObject;
    }

    protected T createFormObject(RequestContext context) {
        try {
            return getFormObjectClass().newInstance();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    protected User getCurrentUser(RequestContext context) {
        return (User) context.getExternalContext().getSessionMap().get(Constants.CURRENT_USER_SESSION_KEY);
    }

    protected Application getCurrentApplication(RequestContext context) {
        return (Application) context.getExternalContext().getSessionMap().get(Constants.CURRENT_APPLICATION_SESSION_KEY);
    }


    protected String getRequestParameter(RequestContext context, String parameter) {
        return context.getRequestParameters().get(parameter);
    }

    protected ParameterMap getRequestParameters(RequestContext context) {
        return context.getRequestParameters();
    }


    protected String getApplicationUrl(RequestContext context) {
        ExternalContext externalContext = context.getExternalContext();
        try {
            String applicationUrl = (String) externalContext.getApplicationMap().get(Constants.APPLICATION_URL);
            if (applicationUrl == null) {
                Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource(Constants.APPLICATION_CONFIG));
                applicationUrl = props.getProperty("application.url");
                externalContext.getApplicationMap().put(Constants.APPLICATION_URL, applicationUrl);
            }
            return applicationUrl;
        } catch (IOException e) {
            logger.error(this.toString(), e);
            return null;
        }
    }

    protected Event doExecute(RequestContext context) throws Exception {
        String methodName = context.getAttributes().getString("method");
        return (Event) getMethodInvoker().invoke(methodName, context);
    }


    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }

    @Autowired
    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    public DispatchMethodInvoker getMethodInvoker() {
        return methodInvoker;
    }
}
