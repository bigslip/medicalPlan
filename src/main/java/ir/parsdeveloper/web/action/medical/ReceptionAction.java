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
import org.apache.poi.ss.formula.functions.Even;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.ArrayList;
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
        Patient formObject = getFormObject(context);
        User currentUser = getCurrentUser(context);
        try {
            receptionService.addPatient(formObject, currentUser);
        } catch (ServiceException e) {
            messageContext.addError(context, e);
            return error();
        }
        return success();
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends Patient> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return daoService.find("select q from Patient q");

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
        putInFlowScope(context, "patient", patient);
        return success();
    }

    public Event planAction2(RequestContext context) throws ActionException {
        List<Patient> patientList = (List) getSelectedRowsAsEntityList(context);
        putInFlowScope(context, "patientList", patientList);
        return success();
    }

    @Override
    public Event onListViewEntry(RequestContext context) throws Exception {
        Event event = super.onListViewEntry(context);
        Patient patient = getFormObject(context);
        if (patient.getPerson() == null) {
            Person person = new Person();
            patient.setPerson(person);
        }

        return event;
    }

    public Event searchAction(RequestContext context) throws ActionException {
        Patient patient = getFormObject(context);
        Person person = patient.getPerson();
        List<Patient> patientList = (List) daoService.createQuery("select pa from Patient pa inner join pa.person p where p.nationalId=:nationalId")
                .setParameter("nationalId", person.getNationalId()).getResultList();
        putInFlowScope(context, "tempPatientList", patientList);
        putInFlowScope(context,"visiable",false);
        return success();
    }

    public Event selectAction(RequestContext context) throws ActionException {
        Patient patient = getSelectedRowAsEntity(context);
        List<Patient> patientAddedList = (List) getFromFlowScope(context, "patientAddedList");
        if (patientAddedList == null) {
            patientAddedList = new ArrayList<>();
        }
        patientAddedList.add(patient);
        putInFlowScope(context, "patientAddedList", patientAddedList);
        putInFlowScope(context,"visiable",true);
        return success();
    }
}
