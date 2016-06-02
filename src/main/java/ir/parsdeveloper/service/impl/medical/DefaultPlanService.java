package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.*;
import ir.parsdeveloper.service.api.business.core.RoleService;
import ir.parsdeveloper.service.api.medical.PlanService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bahram on 5/25/16.
 */
@Service
public class DefaultPlanService extends DefaultBasicService<Plan> implements PlanService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Plan addPlan(Plan plan, User user) throws ServiceException {

        List<Doctor> doctorList = (List<Doctor>) daoService.createQuery("select d from Doctor d inner join d.user u where u.id=:userId")
                .setParameter("userId", user.getId()).getResultList();

        if (doctorList == null || doctorList.isEmpty()) {
            throw new ServiceException("A doctor can determine a patient's program");
        }
        if (doctorList.size() > 1) {
            throw new ServiceException("An unknown error occurred");
        }

        Doctor doctor = doctorList.get(0);
        Period period = plan.getPeriod();
        PlanTemplate planTemplate = period.getPlanTemplate();

        planTemplate.setDoctor(doctor);
        planTemplate = saveEntity(planTemplate, user);

        period.setPlanTemplate(planTemplate);
        period.setDoctor(doctor);
        period = saveEntity(period, user);

        plan.setPeriod(period);
        PlanStatus planStatus = new PlanStatus(1L);
        plan.setPlanStatus(planStatus);
        plan = saveEntity(plan, user);
        return plan;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPatientPlan(Plan plan, List<Patient> patientList) throws ServiceException {
        plan.getPatientList().addAll(patientList);
        // plan.setPatientList(patientList);

        daoService.save(plan);
    }

}
