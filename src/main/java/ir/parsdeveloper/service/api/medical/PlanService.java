package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Patient;
import ir.parsdeveloper.model.entity.core.Plan;
import ir.parsdeveloper.model.entity.core.User;

import java.util.List;

/**
 * Created by bahram on 5/25/16.
 */
public interface PlanService {
    Plan addPlan(Plan plan, User user) throws ServiceException;

    void addPatientPlan(Plan plan, List<Patient> patientList) throws ServiceException;
}
