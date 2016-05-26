package ir.parsdeveloper.web.action.core;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.MessageCodes;
import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.StringUtils;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.service.api.business.core.BasicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author m.namzi & h.tayebi
 */
//@Scope(value = WebApplicationContext.SCOPE_APPLICATION)
@SuppressWarnings("unchecked")
public abstract class AbstractFormAction<T extends BaseModel> extends FormAction<T> implements Serializable {

    public final static String CURRENT_PAGE = "currentPage";
    public final static String PAGE_SIZE = "PAGE_SIZE";
    public final static String TOTAL_ROWS = "TOTAL_ROWS";
    public final static String TOTAL_PAGE = "totalPage";
    public final static String PAGINATION_COMMAND = "paginationCommand";
    public static final String LAST_FORM_OBJECT = "lastFormObject";
    protected String applicationCode;

    public AbstractFormAction(Class<? extends T> formObjectClass) {
        super(formObjectClass);
    }

    public Event initFlow(RequestContext context) throws BaseException {
        T formObject = (T) getFromFlowScope(context, Constants.FORM_OBJECT_VALUE);
        if (formObject != null) {
            putFormObject(context, formObject);
        }
        String pageMode = (String) getFromFlowScope(context, Constants.PAGE_MODE);
        if (pageMode == null) {
            putInFlowScope(context, Constants.PAGE_MODE, Constants.UPDATE_PAGE_MODE);
        }
        initViewNames(context);
        initIterator(context);
        return success();
    }

    protected void initViewNames(RequestContext context) {
        String formObjectName = getFormObjectName();
        putInFlowScope(context, Constants.FORM_OBJECT_NAME, formObjectName);
        putInFlowScope(context, FormAction.ENTITY_LIST_VIEW, getApplicationCode() + "/" + formObjectName + "/" + formObjectName + Constants.LIST_VIEW_SUFFIX);
        putInFlowScope(context, FormAction.ENTITY_SINGLE_VIEW, getApplicationCode() + "/" + formObjectName + "/" + formObjectName + Constants.SINGLE_VIEW_SUFFIX);
        putInFlowScope(context, FormAction.ERROR_SINGLE_VIEW, FormAction.ERROR_SINGLE_VIEW);
    }

    public Event listAction(RequestContext context) throws ActionException {
        try {
            Object lastFormObject = getFromFlowScope(context, LAST_FORM_OBJECT);
            if (lastFormObject != null && lastFormObject instanceof BaseModel) {
                putFormObject(context, (BaseModel) lastFormObject);
            }

            Integer currentPage = 0;
            String cn = getRequestParameter(context, CURRENT_PAGE);
            if (StringUtils.hasText(cn)) {
                currentPage = Integer.parseInt(cn);
            }
            Integer pageSize = (Integer) getFromFlowScope(context, PAGE_SIZE);
            if (pageSize == null) {
                putInFlowScope(context, PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
                pageSize = Constants.DEFAULT_PAGE_SIZE;
            }


            currentPage = (currentPage < 1) ? 1 : currentPage;
            List<? extends T> entityList = prepareEntityList(context, currentPage, pageSize);
            long count = getCountEntity(context);

            putInFlowScope(context, CURRENT_PAGE, currentPage);
            putInFlowScope(context, TOTAL_ROWS, count);
            putInFlowScope(context, TOTAL_PAGE, (int) (count / pageSize) + 1);

            putEntityList(context, entityList);
            return success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActionException(e);
        }
    }

    public abstract Event saveEntity(RequestContext context) throws ActionException, ServiceException;

    public abstract Event deleteEntity(RequestContext context) throws ActionException;

    protected abstract List<? extends T> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException;

    public abstract long getCountEntity(RequestContext context) throws BaseException;

    public Event onEdit(RequestContext context) throws ActionException {
        BaseModel selectedEntity = getSelectedRowAsEntity(context);
        if (selectedEntity == null) {
            throw new ActionException(MessageCodes.UNKNOWN_ERROR_MESSAGE);
        }
        putFormObject(context, selectedEntity);
        return success();
    }

    public Event onListViewEntry(RequestContext context) throws Exception {
        Object lastFormObject = getFromFlowScope(context, LAST_FORM_OBJECT);
        if (lastFormObject != null && lastFormObject instanceof BaseModel) {
            putFormObject(context, (BaseModel) lastFormObject);
        }
        return success();
    }

    public Event onListViewExit(RequestContext context) throws ActionException {
        T formObject = getFormObject(context);
        putInFlowScope(context, LAST_FORM_OBJECT, formObject);
        return success();
    }

    public Event onAdd(RequestContext context) throws ActionException {
        resetForm(context);
        return success();
    }

    public Event initListView(RequestContext context) throws Exception {
        getFormObject(context);
        return success();
    }

    public Event setupForm(RequestContext context) throws Exception {
        getFormObject(context);
        return success();
    }

    public Event onRenderSingleView(RequestContext context) throws ActionException {
        return success();
    }

    public Event changePageAction(RequestContext context) {
        String command = (String) getFromFlowScope(context, PAGINATION_COMMAND);
        Integer currentPage = (Integer) getFromFlowScope(context, CURRENT_PAGE);
        Integer totalPage = (Integer) getFromFlowScope(context, TOTAL_PAGE);
        switch (command) {
            case "next":
                currentPage = currentPage % totalPage + 1;
                break;
            case "previous":
                currentPage = (currentPage > 1) ? currentPage - 1 : 1;
                break;
            case "first":
                currentPage = 1;
                break;
            case "last":
                currentPage = totalPage;
                break;
        }
        putInFlowScope(context, CURRENT_PAGE, currentPage);
        return success();
    }

    protected void initIterator(RequestContext context) {
        putInFlowScope(context, CURRENT_PAGE, 0);
        putInFlowScope(context, TOTAL_PAGE, 0);
        putInFlowScope(context, PAGE_SIZE, Constants.DEFAULT_PAGE_SIZE);
    }

    protected void putEntityList(RequestContext context, List<? extends T> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            entityList = new ArrayList<T>(0);
        }
        putInFlowScope(context, getFormObjectName() + Constants.ENTITY_LIST, entityList);
    }

    protected List<T> getEntityList(RequestContext context) {
        return (List) getFromFlowScope(context, getFormObjectName() + Constants.ENTITY_LIST);
    }

    protected T getSelectedRowAsEntity(RequestContext context) throws ActionException {
        String selectedIdStr = getRequestParameters(context).get(getFormObjectName() + Constants.SELECTED_ROWS);
        if (!StringUtils.hasText(selectedIdStr)) {
            throw new ActionException("errors.unknown_error");
        }
        String[] rows = selectedIdStr.split(";");
        if (!StringUtils.hasText(selectedIdStr)) {
            throw new ActionException("errors.unknown_error");
        }

        Integer index = Integer.parseInt(rows[0]);
        List<T> entityList = getEntityList(context);
        return entityList.get(index - 1);
    }

    protected Integer getSelectedRow(RequestContext context) throws ActionException {
        String selectedIdStr = getRequestParameters(context).get(Constants.SELECTED_ROWS);
        if (!StringUtils.hasText(selectedIdStr) || selectedIdStr.equals("-1")) {
            throw new ActionException("errors.unknown_error");
        }
        return Integer.parseInt(selectedIdStr) - 1;
    }

    protected String[] getSelectedRowsId(RequestContext context) throws ActionException {
        String selectedIdStr = getRequestParameters(context).get(getFormObjectName() + Constants.SELECTED_ROWS);
        if (!StringUtils.hasText(selectedIdStr)) {
            throw new ActionException("errors.unknown_error");
        }
        String[] rows = selectedIdStr.split(";");
        return rows;
        // return getRequestParameters(context).getArray(FormAction.SelectedRow);
    }

    protected List<BaseModel> getSelectedRowsAsEntityList(RequestContext context) throws ActionException {
        String selectedRow[] = getSelectedRowsId(context);
        if (selectedRow == null || selectedRow.length == 0) return null;

//        Map<Integer, T> entityMap = new HashMap<>(5);
        List<T> entityList = getEntityList(context);
        List<BaseModel> list = new ArrayList<BaseModel>();
        for (String rowId : selectedRow) {
            try {
                Integer id = Integer.parseInt(rowId);
                list.add(entityList.get(id - 1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    protected abstract BasicService<T> getBasicService();

    public String getApplicationCode() {
        return applicationCode;
    }

    @Value("${application.code}")
    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode.toLowerCase();
    }
}
