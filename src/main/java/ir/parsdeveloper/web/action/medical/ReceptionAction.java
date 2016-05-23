package ir.parsdeveloper.web.action.medical;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Patient;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.Role;

import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.business.core.RoleService;
import ir.parsdeveloper.service.api.medical.ReceptionService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import ir.parsdeveloper.ws.client.SampleWebServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.Date;
import java.util.List;

/**
 * @author m.namzi & h.tayebi
 */
@Component
//@Scope
public class ReceptionAction extends AbstractFormAction<Patient> {
    private ReceptionService receptionService;

    public ReceptionAction() {
        super(Patient.class);
    }

    @Override
    public void initAction() {
        super.initAction();
    }

    @Override
    public Event setupForm(RequestContext context) throws Exception {
        Event event = super.setupForm(context);
        Patient patient = getFormObject(context);
        if (patient.getPerson() == null) {
            Person person = new Person();
            patient.setPerson(person);
        }
        return event;
    }

    @Override
    public Event saveEntity(RequestContext context) throws ActionException, ServiceException {
//        Person formObject = getFormObject(context);
//        formObject.setBirthDate(new Date());
//        User user=new User();
//        user.setId(1L);
//        receptionService.addPerson(formObject, user);
        return success();
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends Patient> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return null;
    }

    @Override
    public long getCountEntity(RequestContext context) throws BaseException {
        return 0;
    }

    @Override
    protected BasicService<Patient> getBasicService() {
        return null;
    }

    @Autowired
    public void setReceptionService(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    public Event nextAction(RequestContext context) throws BaseException {
        Patient patient = getFormObject(context);
        putInFlowScope(context,"patient",patient);
        return success();
    }


}
