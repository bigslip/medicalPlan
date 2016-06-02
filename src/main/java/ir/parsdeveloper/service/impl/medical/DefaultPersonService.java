package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.BaseModel;
import ir.parsdeveloper.model.entity.core.Doctor;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.UserSetupService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bahram on 5/26/16.
 */
@Service
public class DefaultPersonService<T extends BaseModel> extends DefaultBasicService<T> {
    protected UserSetupService userSetupService;

    @Transactional(rollbackFor = Exception.class)
    Person addPerson(Person person, User user) throws ServiceException {

//            List personList = daoService.createQuery("from Person p where p.nationalId=:nationalId")
//                    .setParameter("nationalId", person.getNationalId()).getResultList();
//
//            if (personList != null && !personList.isEmpty()) {
//                throw new ServiceException("Someone already has that  nationalId ");
//            }

        person.setFixedName(CommonUtils.fixString(person.getFirstName() + person.getLastName()));
        return saveEntity(person, user);

    }

    @Autowired
    public void setUserSetupService(UserSetupService userSetupService) {
        this.userSetupService = userSetupService;
    }
}
