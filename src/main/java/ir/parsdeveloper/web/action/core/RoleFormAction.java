package ir.parsdeveloper.web.action.core;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.business.core.RoleService;
import ir.parsdeveloper.ws.client.SampleWebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * @author m.namzi & h.tayebi
 */
@Component
//@Scope
public class RoleFormAction extends AbstractFormAction<Role> {

    RoleService roleService;
    SampleWebServiceClient webServiceClient;


    public RoleFormAction() {
        super(Role.class);
    }

    @Override
    public void initAction() {
        super.initAction();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public Event initFlow(RequestContext context) throws BaseException {
        // adminMessageSender.senMessage("hello jms message");
//        webServiceClient.callWs();
        //roleService.test();
        roleService.getUserRole(getCurrentUser(context));
        putInFlowScope(context, "applicationList", roleService.getSubSystemList());
        return super.initFlow(context);
    }

    protected List<Role> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return daoService.find("select q from Role q");
    }

    public long getCountEntity(RequestContext context) throws BaseException {
        return 10;// getBasicService().getCountEntity(getFormObject(context), getCurrentUser(context));
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public Event saveEntity(RequestContext context) throws ActionException {
        Role entity = getFormObject(context);
        try {
            roleService.addRole(entity, getCurrentUser(context));
        } catch (ServiceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        resetForm(context);
        messageContext.addInfo(context, "action.successful");
        return success();
    }


    public Event deleteEntity(RequestContext context) throws ActionException {
        Role role = getSelectedRowAsEntity(context);
        roleService.deleteEntity(role, getCurrentUser(context));
        messageContext.addInfo(context, "action.successful");
        return success();

    }

    public Event testAction(RequestContext context) throws ActionException {
        Role role = getSelectedRowAsEntity(context);
        putInFlowScope(context, "selectedEntity", role);
        messageContext.addInfo(context, "action.successful");
        return success();

    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    protected BasicService<Role> getBasicService() {
        return this.roleService;
    }

    @Autowired
    public void setWebServiceClient(SampleWebServiceClient webServiceClient) {
        this.webServiceClient = webServiceClient;
    }


}
