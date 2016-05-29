package ir.parsdeveloper.service.api.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;

/**
 * Created by bahram on 5/26/16.
 */
public interface UserSetupService {
    User addMedicalUser(User user, Person person, User creatorUser) throws ServiceException;
}
