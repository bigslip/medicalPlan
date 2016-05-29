package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Nurse;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.RegisterNurseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by bahram on 5/27/16.
 */
@Service
public class DefaultRegisterNurseService extends DefaultPersonService<Nurse> implements RegisterNurseService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Nurse addNurse(Nurse nurse, User currentUser) throws ServiceException {
        if (nurse.getPerson() == null) {
            throw new ServiceException("error.person_is_null");
        }
        Person person = nurse.getPerson();
        User nurseUser = new User();

        person = addPerson(person, currentUser);

        nurse.setPerson(person);
        nurse = saveEntity(nurse, currentUser);

        Map<String, Object> params = new HashMap<>(1);
        params.put("roleId", Arrays.asList(Constants.RECEPTION_ROLE));
        List<Role> roleList = daoService.findByNamedQuery(Role.GET_ROLES_BY_ROLE_ID, params);
        nurseUser.setRoles(new HashSet<>(roleList));

        userSetupService.addMedicalUser(nurseUser, person, currentUser);
        return nurse;
    }
}
