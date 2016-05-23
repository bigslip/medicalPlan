package ir.parsdeveloper.web.action;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * @author hadi tayebi
 */
public class TestFormAction extends AbstractFormAction<Role> {

    public TestFormAction() {
        super(Role.class);
    }

    @Override
    public Event saveEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends Role> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return null;
    }

    public long getCountEntity(RequestContext context) throws BaseException {
        return 10;
    }

    @Override
    protected BasicService<Role> getBasicService() {
        return null;
    }
}
