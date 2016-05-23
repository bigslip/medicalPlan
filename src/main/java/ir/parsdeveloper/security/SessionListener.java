package ir.parsdeveloper.security;


import ir.parsdeveloper.commons.Constants;
import ir.parsdeveloper.commons.utils.StringUtils;
import ir.parsdeveloper.model.entity.core.Application;
import ir.parsdeveloper.model.entity.core.Menu;
import ir.parsdeveloper.model.entity.core.User;
import ir.parsdeveloper.service.api.business.core.OnlineUserHandler;
import ir.parsdeveloper.service.api.business.core.SecurityService;
import ir.parsdeveloper.service.api.dao.DaoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author hadi tayebi
 */
@Service
public class SessionListener implements AuthenticationSuccessHandler,
        LogoutSuccessHandler, AuthenticationFailureHandler, Serializable {

    private final static String AUTHENTICATION_FAILURE_URL = "/login?bad_credentials";
    protected Log logger = LogFactory.getLog(SessionListener.class);
    protected SecurityService securityService;
    protected DaoService daoService;
    protected OnlineUserHandler onlineUserHandler;

    @Value("${application.code}")
    private String applicationCode;

    @Value("${welcome.url}")
    private String welcomeUrl;

    @Autowired
    public SessionListener(DaoService daoService, SecurityService securityService) {
        this.daoService = daoService;
        this.securityService = securityService;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            Object principal = authentication.getPrincipal();

            UserDetails userDetails = (UserDetails) principal;

            onlineUserHandler.addUser(onlineUserHandler.createDefaultOnlineUser(userDetails, request.getHeader("agent"), new DateTime()));

            User currentUser = securityService.findUserByUserName(userDetails.getUsername());
            if (StringUtils.isEmpty(applicationCode)) {
                throw new IllegalArgumentException("application code is required");
            }
            Application currentApplication = securityService.getApplicationByCode(applicationCode);

            List<Menu> userMenuList = securityService.loadUserMenu(currentUser, currentApplication);

            request.getSession().setAttribute(Constants.CURRENT_APPLICATION_SESSION_KEY, currentApplication);
            request.getSession().setAttribute(Constants.CURRENT_USER_SESSION_KEY, currentUser);
            request.getSession().setAttribute(Constants.MENU_LIST_KEY, userMenuList);
            request.getSession().setAttribute("onlineUserHandler", onlineUserHandler);

            securityService.registerLoginHistory(currentApplication, currentUser, request.getRemoteAddr());

            SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
            if (savedRequest != null) {
                response.sendRedirect(savedRequest.getRedirectUrl());
            } else {
                response.sendRedirect(welcomeUrl);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e);
        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getPrincipal() != null) {
            onlineUserHandler.removeUser((UserDetails) authentication.getPrincipal());
        }
        response.sendRedirect(welcomeUrl);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + AUTHENTICATION_FAILURE_URL);
    }

    @Autowired
    public void setOnlineUserHandler(OnlineUserHandler onlineUserHandler) {
        this.onlineUserHandler = onlineUserHandler;
    }
}
