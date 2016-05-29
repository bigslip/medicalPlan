package ir.parsdeveloper.service.impl.medical;

import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.commons.utils.CommonUtils;
import ir.parsdeveloper.model.entity.core.Person;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.medical.UserSetupService;
import ir.parsdeveloper.service.impl.business.core.DefaultBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by bahram on 5/26/16.
 */
@Service
public class DefaultUserSetupService extends DefaultBasicService<User> implements UserSetupService {
    private Md5PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addMedicalUser(User user, Person person, User creatorUser) throws ServiceException {
        user.setEmail(person.getEmail());
        user.setFixedName(CommonUtils.fixString(person.getFirstName() + person.getLastName()));
        user.setNickName(person.getFirstName() + " " + person.getLastName());
        user.setUsername(person.getNationalId().toString());
        user.setPassword(passwordEncoder.encodePassword(Constants.DEFAULT_PASSWORD, null));
        user.setRegisterDate(new Date());
        return saveEntity(user, creatorUser);
    }

    @Autowired
    @Qualifier(value = "passwordEncoder")
    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
