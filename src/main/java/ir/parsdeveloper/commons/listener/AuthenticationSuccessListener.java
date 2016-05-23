package ir.parsdeveloper.commons.listener;

import ir.parsdeveloper.service.api.business.core.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author hadi tayebi
 */

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private SecurityService securityService;

    @Value("${application.code}")
    private String applicationCode;


    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        UserDetails principal = (UserDetails) event.getAuthentication().getPrincipal();
        //securityService.registerLoginHistory(applicationCode,principal.getUsername(),event.getAuthentication().);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}

