package ir.parsdeveloper.web.action.medical;

import ir.parsdeveloper.commons.exception.ActionException;
import ir.parsdeveloper.commons.exception.BaseException;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.*;
import ir.parsdeveloper.service.api.business.core.BasicService;
import ir.parsdeveloper.service.api.medical.PlanService;
import ir.parsdeveloper.web.action.core.AbstractFormAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bahram on 5/21/16.
 */
@Component
public class PlanAction extends AbstractFormAction<Plan> {
    private PlanService planService;

    public PlanAction() {
        super(Plan.class);
    }


    @Override
    public Event setupForm(RequestContext context) throws Exception {
        Event event = super.setupForm(context);
        Plan plan = getFormObject(context);

        if (plan.getPeriod() == null) {
            Period period = new Period();
            plan.setPeriod(period);
            if (period.getPlanTemplate() == null) {
                PlanTemplate planTemplate = new PlanTemplate();
                period.setPlanTemplate(planTemplate);
            }
        }

        return event;
    }

    @Override
    public void initAction() {
        super.initAction();
    }

    @Override
    public Event saveEntity(RequestContext context) throws ActionException, ServiceException {
        Plan plan = getFormObject(context);
        try {
            planService.addPlan(plan, getCurrentUser(context));
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
    protected List<? extends Plan> prepareEntityList(RequestContext context, int currentPage, int pageSize) throws BaseException {
        List<Plan> planList;

        planList = daoService.find("select q from Plan q order by q.creationDate");

        if (planList.isEmpty()) {
            return new ArrayList<>();
        }
        return planList;
    }

    @Override
    public long getCountEntity(RequestContext context) throws BaseException {
        return 0;
    }

    @Override
    protected BasicService<Plan> getBasicService() {
        return null;
    }

    @Autowired
    public void setPlanService(PlanService planService) {
        this.planService = planService;
    }

    public Event patientPlanAction(RequestContext context) throws ActionException, ServiceException {
        List<Plan> planList = (List) getSelectedRowsAsEntityList(context);
        List<Patient> patientList = (List) getFromFlowScope(context, "patientList");
        try {
            planService.addPatientPlan(planList.get(0), patientList);
        } catch (ServiceException e) {
            messageContext.addError(context, e);
            return error();
        }
        return success();
    }
}
