package ir.parsdeveloper.web.action.medical;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Nurse;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.medical.RegisterNurseService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * Created by bahram on 5/27/16.
 */
@Component
public class RegisterNurseAction extends AbstractFormAction<Nurse> {
    private RegisterNurseService registerNurseService;

    public RegisterNurseAction() {
        super(Nurse.class);
    }

    @Override
    public Event saveEntity(RequestContext context) throws ActionException, ServiceException {
        Nurse nurse = getFormObject(context);

        try {
            registerNurseService.addNurse(nurse, getCurrentUser(context));
        } catch (ServiceException e) {
            messageContext.addError(context, e);
            return error();
        }
        return success();
    }

    @Override
    public Event setupForm(RequestContext context) throws Exception {
        Event event = super.setupForm(context);
        Nurse nurse = getFormObject(context);
        if (nurse.getPerson() == null) {
            Person person = new Person();
            nurse.setPerson(person);
        }
        return event;
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends Nurse> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return daoService.find("select q from Nurse q");
    }

    @Override
    public long getCountEntity(RequestContext context) throws BaseException {
        return 0;
    }

    @Override
    protected BasicService<Nurse> getBasicService() {
        return null;
    }

    @Autowired
    public void setRegisterNurseService(RegisterNurseService registerNurseService) {
        this.registerNurseService = registerNurseService;
    }
}
