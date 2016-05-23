package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Patient;
import ir.parsdeveloper.model.entity.core.PlanTemplate;
import ir.parsdeveloper.model.entity.core.User;

/**
 * Created by bahram on 5/23/16.
 */
public interface PlanTemplateService {
    PlanTemplate addPlanTemplate(PlanTemplate planTemplate, Patient patient, User user) throws ServiceException;
}
