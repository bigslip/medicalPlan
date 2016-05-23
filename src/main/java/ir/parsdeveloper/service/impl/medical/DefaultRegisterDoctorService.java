package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.Doctor;

import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.RegisterDoctorService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by bahram on 5/22/16.
 */
@Service
public class DefaultRegisterDoctorService extends DefaultBasicService<Doctor> implements RegisterDoctorService {
    @Override
    public Doctor addDoctor(Doctor doctor) throws ServiceException {
        if (doctor.getPerson() == null) {
            throw new ServiceException("error.person_is_null");
        }
        Person person = doctor.getPerson();
        person.setBirthDate(new Date());
        User user=new User();
        user.setId(1L);
        person.setCreator(user);
        person.setFixedName(CommonUtils.fixString(person.getFirstName() + person.getLastName()));
        person = saveEntity(person);
        doctor.setPerson(person);
        return saveEntity(doctor);

    }
}
