package ir.parsdeveloper.service.impl.business.core;


import ir.parsdeveloper.commons.annotations.Stopwatch;
import ir.parsdeveloper.commons.exception.ServiceException;
import ir.parsdeveloper.model.entity.core.*;
import ir.parsdeveloper.service.api.business.core.SecurityService;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * @author hadi tayebi
 */
@Service
public class DefaultSecurityService implements SecurityService, UserDetailsService, Serializable {

    protected DaoService daoService;
    protected Md5PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        User user = findUserByUserNameWithRoles(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        Collection<GrantedAuthority> authorities;
        Set<Role> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {
            authorities = new HashSet<>(roles.size());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getCode()));
            }
        } else {
            authorities = new HashSet<>(0);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                , user.getPassword()
                , user.getActive()
                , user.getExpired()
                , false//credentialsNonExpired
                , user.getLocked()
                , authorities);
    }

    @Transactional(readOnly = true)
    public User findUserByUserName(String username) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("username", username);
        List<User> list = daoService.findByNamedQuery(User.FIND_BY_USER_NAME, params);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public User findUserByUserNameWithRoles(String username) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("username", username);

        List<User> list = daoService.findByNamedQuery(User.FIND_BY_USER_NAME_WITH_ROLES, params);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public Application getApplicationByCode(String applicationCode) throws ServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("applicationCode", applicationCode);
        List<Application> resultList = daoService.findByNamedQuery(Application.FIND_BY_APPLICATION_CODE, params);
        if (resultList == null || resultList.size() == 0) {
            throw new ServiceException("");
        }
        return resultList.get(0);
    }

    public List<Menu> loadUserMenu(User currentUser, Application application) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("user", currentUser);
        params.put("application", application);
        List<Menu> menuList = daoService.findByNamedQuery(Menu.GET_USER_MAIN_MENU_LIST, params);
        List<Menu> userMenuList = new ArrayList<Menu>(menuList.size());
        for (Menu menu : menuList) {
            Menu parent = menu.getParent();
            if (parent.getParent() != null) {
                continue;
            }
            int indexOf = userMenuList.indexOf(parent);
            if (indexOf == -1) {
                parent.setSubMenu(new ArrayList<Menu>());
                userMenuList.add(parent);
            } else {
                userMenuList.get(indexOf);
            }
            parent.getSubMenu().add(menu);
        }
        return userMenuList;
    }

    @Stopwatch
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Throwable.class, timeout = -1)
    public void registerLoginHistory(Application application, User user, String ipAddress) {
        LoginHistory history = new LoginHistory();
        history.setUser(user);
        history.setApplication(application);
        history.setLoginTime(new Date());
        history.setIpAddress(ipAddress);
        daoService.save(history);
    }

    @Autowired
    public void setDaoService(DaoService daoService) {
        this.daoService = daoService;
    }

    @Autowired
    @Qualifier(value = "passwordEncoder")
    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
