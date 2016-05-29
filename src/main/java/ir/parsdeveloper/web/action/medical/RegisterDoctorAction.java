package ir.parsdeveloper.web.action.medical;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Doctor;

import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.medical.RegisterDoctorService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * Created by bahram on 5/22/16.
 */
@Component
public class RegisterDoctorAction extends AbstractFormAction<Doctor> {
    public RegisterDoctorAction() {
        super(Doctor.class);
    }

    private RegisterDoctorService registerDoctorService;

    @Override
    public Event setupForm(RequestContext context) throws Exception {
        Event event = super.setupForm(context);
        Doctor doctor = getFormObject(context);
        if (doctor.getPerson() == null) {
            Person person = new Person();
            doctor.setPerson(person);
        }
        return event;
    }

    @Override
    public void initAction() {
        super.initAction();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Event saveEntity(RequestContext context) throws ActionException, ServiceException {
        Doctor doctor = getFormObject(context);

        try {
            registerDoctorService.addDoctor(doctor, getCurrentUser(context));
        } catch (ServiceException e) {
            messageContext.addError(context, e.getMessage());
            return error();
        }

        return success();
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends Doctor> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return daoService.find("select q from Doctor q");
    }

    @Override
    public long getCountEntity(RequestContext context) throws BaseException {
        return 0;
    }

    @Override
    protected BasicService<Doctor> getBasicService() {
        return null;
    }

    @Autowired
    public void setRegisterDoctorService(RegisterDoctorService registerDoctorService) {
        this.registerDoctorService = registerDoctorService;
    }
}
