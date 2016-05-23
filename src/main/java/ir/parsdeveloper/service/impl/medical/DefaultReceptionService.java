package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.ReceptionService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by bahram on 5/18/16.
 */
@Service
public class DefaultReceptionService extends DefaultBasicService<Person> implements ReceptionService {


    @Override
    @Transactional(rollbackFor = Throwable.class, timeout = -1)
    public Person addPerson(Person person, User currentUser) throws ServiceException {
        person.setFixedName(CommonUtils.fixString(person.getFirstName()));
        person.setCreationDate(new Date());
        person.setCreator(currentUser);
        person = saveEntity(person);
        return person;

    }


}
