package ir.parsdeveloper.config;

import ir.parsdeveloper.security.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SpringDigestPasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.xwss.callback.SpringPlainTextPasswordValidationCallbackHandler;

import javax.security.auth.callback.CallbackHandler;
import javax.sql.DataSource;

/**
 * @author hadi tayebi
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, mode = AdviceMode.ASPECTJ, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.ignoringRequestPatterns}")
    protected String[] ignoringRequestPatterns;

    @Value("${security.unSecuredRequestPatterns}")
    protected String[] unSecuredRequestPatterns;

    @Value("${security.securedRequestPatterns}")
    protected String[] securedRequestPatterns;

    protected ApplicationEventPublisher applicationEventPublisher;
    protected SessionListener sessionListener;
    protected UserDetailsService userDetailsService;

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(unSecuredRequestPatterns).permitAll()
                .antMatchers(securedRequestPatterns).authenticated();

        http.formLogin().loginPage("/login").successHandler(sessionListener);
        http.logout().logoutUrl("/logout").logoutSuccessHandler(sessionListener);

        http.sessionManagement().maximumSessions(1);
        http.exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignoringRequestPatterns);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.authenticationEventPublisher(authenticationEventPublisher());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        return new DefaultWebSecurityExpressionHandler();
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public XwsSecurityInterceptor xwsSecurityInterceptor(CallbackHandler webServiceSecurityHandler) throws Exception {
        XwsSecurityInterceptor xwsSecurityInterceptor = new XwsSecurityInterceptor();
        xwsSecurityInterceptor.setPolicyConfiguration(new ClassPathResource("/ir/parsdeveloper/ws/securityPolicy.xml"));
        xwsSecurityInterceptor.setCallbackHandler(webServiceSecurityHandler);
        //xwsSecurityInterceptor.setCallbackHandlers();
        return xwsSecurityInterceptor;
    }

    @Bean
    public SpringDigestPasswordValidationCallbackHandler digestPasswordValidationCallbackHandler() throws Exception {
        SpringDigestPasswordValidationCallbackHandler springWsSecurityHandler = new SpringDigestPasswordValidationCallbackHandler();
        springWsSecurityHandler.setUserDetailsService(userDetailsService);
        return springWsSecurityHandler;
    }

    //@Bean
    public SpringPlainTextPasswordValidationCallbackHandler plainTextPasswordValidationCallbackHandler(AuthenticationManager authenticationManager) throws Exception {
        SpringPlainTextPasswordValidationCallbackHandler springWsSecurityHandler = new SpringPlainTextPasswordValidationCallbackHandler();
        springWsSecurityHandler.setAuthenticationManager(authenticationManager);
        return springWsSecurityHandler;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Autowired
    public void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
    }

    @Autowired
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
