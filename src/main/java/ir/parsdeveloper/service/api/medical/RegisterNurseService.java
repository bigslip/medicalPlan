package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Nurse;
import ir.parsdeveloper.model.entity.core.User;

/**
 * Created by bahram on 5/27/16.
 */
public interface RegisterNurseService {
    Nurse addNurse(Nurse nurse, User currentUser) throws ServiceException;
}
