package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.*;
import ir.parsdeveloper.service.api.medical.PlanTemplateService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bahram on 5/23/16.
 */
@Service
public class DefaultPlanTemplateService extends DefaultBasicService<PlanTemplate> implements PlanTemplateService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlanTemplate addPlanTemplate(PlanTemplate planTemplate, Patient patient, User user) throws ServiceException {
        if (planTemplate.getPeriod() == null) {
            throw new ServiceException("period is null");
        }
        if (patient == null) {
            throw new ServiceException("patient is null");
        }
        if (planTemplate.getDoctorId() == null) {
            throw new ServiceException("doctorId is null");
        }
        Long doctorId = planTemplate.getDoctorId();
        Doctor doctor = daoService.findById(Doctor.class, doctorId);
        patient.setCreator(user);
        patient = saveEntity(patient);

        Period period = planTemplate.getPeriod();
        period.setDoctor(doctor);

        period.setCreator(user);
        period = saveEntity(period);

        planTemplate.setPeriod(period);
        planTemplate.setCreator(user);
        saveEntity(planTemplate);
        return null;
    }
}
