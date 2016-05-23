package ir.parsdeveloper.config;

import ir.parsdeveloper.security.SessionListener;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hadi tayebi
 */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class CasSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    SessionListener sessionHandler;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Value("${cas.ticket.validator.url}")
    String casTicketValidatorUrl;

    @Value("${cas.login.url}")
    String casLoginUrl;

    @Value("${application.url}")
    String applicationUrl;

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(applicationUrl + "/j_spring_cas_security_check");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
        casAuthenticationProvider.setAuthenticationUserDetailsService(authenticationUserDetailsService());
        casAuthenticationProvider.setServiceProperties(serviceProperties());
        casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
        casAuthenticationProvider.setKey("an_id_for_this_auth_provider_only");
        return casAuthenticationProvider;
    }

    @Bean
    public AuthenticationUserDetailsService authenticationUserDetailsService() {
        //todo test !!!
        return new AbstractCasAssertionUserDetailsService() {
            @Override
            protected UserDetails loadUserDetails(Assertion assertion) {
                return userDetailsService.loadUserByUsername(assertion.getPrincipal().getName());
            }
        };
    }

    @Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(casTicketValidatorUrl);
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
        CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
        casAuthenticationFilter.setAuthenticationManager(authenticationManager());
        casAuthenticationFilter.setAuthenticationFailureHandler(sessionHandler);
        casAuthenticationFilter.setAuthenticationSuccessHandler(sessionHandler);
        return casAuthenticationFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        return new SingleSignOutFilter();
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
        CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
        casAuthenticationEntryPoint.setLoginUrl(casLoginUrl);
        casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
        return casAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .antMatchers("/*-flow", "/secured/**")
                .authenticated()
                .and()
                .csrf().disable();

        http.addFilter(casAuthenticationFilter())
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);

        http.logout().invalidateHttpSession(true).logoutSuccessUrl(casTicketValidatorUrl + "/login");
        http.sessionManagement().maximumSessions(1);
        http
                .exceptionHandling().accessDeniedPage("/accessDeniedPage")
                .authenticationEntryPoint(casAuthenticationEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(casAuthenticationProvider());
    }

    ///////////////////////////////////////

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }


/*    @Bean
    public SpringPlainTextPasswordValidationCallbackHandler webServiceSecurityHandler() throws Exception {
        SpringPlainTextPasswordValidationCallbackHandler springWsSecurityHandler = new SpringPlainTextPasswordValidationCallbackHandler();
        springWsSecurityHandler.setAuthenticationManager(authenticationManager());
        return springWsSecurityHandler;
    }*/

    @Bean(name = "passwordEncoder")
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
        list.add(casAuthenticationProvider());
        list.add(daoAuthenticationProvider());
        return new ProviderManager(list);
    }

/*    @Bean
    public XwsSecurityInterceptor xwsSecurityInterceptor() throws Exception {
        XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
        xwsSecurityInterceptor.setPolicyConfiguration(new ClassPathResource("/ir/parsdeveloper/ws/securityPolicy.xml"));
        xwsSecurityInterceptor.setCallbackHandler(webServiceSecurityHandler);
        return xwsSecurityInterceptor;
    }

    Hoy webServiceSecurityHandler;

    @Autowired
    public void setWebServiceSecurityHandler(Hoy webServiceSecurityHandler) {
        this.webServiceSecurityHandler = webServiceSecurityHandler;
    }*/
}
