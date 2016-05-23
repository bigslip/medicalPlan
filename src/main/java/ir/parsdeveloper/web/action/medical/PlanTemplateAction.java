package ir.parsdeveloper.web.action.medical;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Patient;
import ir.parsdeveloper.model.entity.core.Period;
import ir.parsdeveloper.model.entity.core.PlanTemplate;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.model.medical.Doctor;
import ir.parsdeveloper.model.medical.Person;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.medical.PlanTemplateService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.List;

/**
 * Created by bahram on 5/21/16.
 */
@Component
public class PlanTemplateAction extends AbstractFormAction<PlanTemplate> {
    public PlanTemplateAction() {
        super(PlanTemplate.class);
    }

    private PlanTemplateService planTemplateService;

    @Override
    public Event setupForm(RequestContext context) throws Exception {
        Event event = super.setupForm(context);
        PlanTemplate planTemplate = getFormObject(context);

        if (planTemplate.getPeriod() == null) {
            Period period = new Period();
            planTemplate.setPeriod(period);
        }

        List<Doctor> doctorList = daoService.find("select q from Doctor q");
        putInFlowScope(context, "doctorList", doctorList);

        return event;
    }

    @Override
    public void initAction() {
        super.initAction();
    }

    @Override
    public Event saveEntity(RequestContext context) throws ActionException, ServiceException {
        PlanTemplate formObject = getFormObject(context);
        Patient patient = (Patient) getFromFlowScope(context, "patient");
        User user = new User();
        user.setId(1L);
        planTemplateService.addPlanTemplate(formObject, patient, user);
        return success();
    }

    @Override
    public Event deleteEntity(RequestContext context) throws ActionException {
        return null;
    }

    @Override
    protected List<? extends PlanTemplate> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        return null;
    }

    @Override
    public long getCountEntity(RequestContext context) throws BaseException {
        return 0;
    }

    @Override
    protected BasicService<PlanTemplate> getBasicService() {
        return null;
    }

    @Autowired
    public void setPlanTemplateService(PlanTemplateService planTemplateService) {
        this.planTemplateService = planTemplateService;
    }
}
