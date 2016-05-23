package ir.parsdeveloper.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author hadi tayebi
 */
public class IPRoleAuthenticationFilter extends OncePerRequestFilter {

    private String targetRole;
    private List<String> allowedIPAddresses;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Assert.notNull(targetRole, "targetRole is required !!!");
        Assert.notEmpty(allowedIPAddresses, "allowedIPAddresses is empty !!!");

        // before we allow the request to proceed, we'll first get the user's role
        // and see if it's an administrator
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            boolean shouldCheck = false;
            // look if the user is the target role
            for (GrantedAuthority authority : authentication.getAuthorities()) {

                if (authority.getAuthority().equals(targetRole)) {
                    shouldCheck = true;
                    break;
                }
            }
            // if we should check IP, then check

            if (shouldCheck) {
                boolean shouldAllow = false;
                for (String ipAddress : allowedIPAddresses) {
                    if (request.getRemoteAddr().equals(ipAddress)) {
                        shouldAllow = true;
                        break;
                    }
                }
                if (!shouldAllow) {
                    // fail the request
                    throw new AccessDeniedException("Access has been denied for your IP address: " + request.getRemoteAddr());
                }
            }
        } else {
            logger.warn("The IPRoleAuthenticationFilter should be placed after the user has been authenticated in the filter chain.");
        }
        filterChain.doFilter(request, response);
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public void setAllowedIPAddresses(List<String> allowedIPAddresses) {
        this.allowedIPAddresses = allowedIPAddresses;
    }
}
