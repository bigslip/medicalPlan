package ir.parsdeveloper.service.api.business.core;

import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.Application;
import ir.parsdeveloper.model.entity.core.Role;
import ir.parsdeveloper.model.entity.core.User;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * @author m.namzi & h.tayebi
 */
public interface RoleService extends BasicService<Role> {

    List<Role> getUserRole(User user);

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    Role addRole(Role role, User currentUser) throws ServiceException;

    List<Application> getSubSystemList() throws ServiceException;

    void test();

}
