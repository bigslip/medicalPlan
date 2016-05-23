package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Application;
import ir.parsdeveloper.model.entity.core.Menu;
import ir.parsdeveloper.model.entity.core.User;

import java.util.List;

/**
 * @author hadi tayebi
 */

public interface SecurityService {

    User findUserByUserName(String username);

    User findUserByUserNameWithRoles(String username);

    Application getApplicationByCode(String applicationCode) throws ServiceException;

    List<Menu> loadUserMenu(User currentUser, Application application);

    void registerLoginHistory(Application application, User user, String ipAddress);
}
