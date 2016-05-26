package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Doctor;
import ir.parsdeveloper.model.entity.core.User;

/**
 * Created by bahram on 5/22/16.
 */
public interface RegisterDoctorService {
    Doctor addDoctor(Doctor doctor,User user) throws ServiceException;
}
