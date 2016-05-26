package ir.parsdeveloper.service.impl.medical;


import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.Doctor;

import ir.parsdeveloper.model.entity.core.Person;

import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.RegisterDoctorService;
import ir.parsdeveloper.service.api.medical.UserSetupService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


/**
 * Created by bahram on 5/22/16.
 */
@Service
public class DefaultRegisterDoctorService extends DefaultPersonService<Doctor> implements RegisterDoctorService {

    private UserSetupService userSetupService;

    @Override
    public Doctor addDoctor(Doctor doctor, User currentUser) throws ServiceException {

        if (doctor.getPerson() == null) {
            throw new ServiceException("error.person_is_null");
        }
        Person person = doctor.getPerson();
        User doctorUser = new User();

        person = addPerson(person,currentUser);

        doctor.setPerson(person);
        doctor = saveEntity(doctor, currentUser);

        Role role = daoService.findById(Role.class, Constants.DOCTOR_ROLE);
        doctorUser.setRoles(Collections.singleton(role));
        userSetupService.addMedicalUser(doctorUser, person, currentUser);

        return doctor;

    }

    @Autowired
    public void setUserSetupService(UserSetupService userSetupService) {
        this.userSetupService = userSetupService;
    }


}
