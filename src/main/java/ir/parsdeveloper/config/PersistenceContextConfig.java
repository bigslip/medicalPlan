package ir.parsdeveloper.config;

import ir.parsdeveloper.commons.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.aspectj.AnnotationTransactionAspect;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author hadi tayebi
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableJpaRepositories(basePackages = {"ir.parsdeveloper.service.impl.business"})
public class PersistenceContextConfig {

    @Value("${dataSource.jndiName}")
    private String dataSourceJndiName;
    @Value("${dataSource.transaction.timeout}")
    private Integer transactionTimeout;

    @Value("${dataSource.url}")
    private String dataSourceUrl;
    @Value("${dataSource.driverClassName}")
    private String dataSourceDriverClass;
    @Value("${dataSource.username}")
    private String dataSourceUsername;
    @Value("${dataSource.password}")
    private String dataSourcePassword;


/*
    @Bean(destroyMethod = "")
    public DataSource dataSource() throws NamingException {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(dataSourceJndiName);
    }*/
   /* @Bean(destroyMethod = "")
    public DataSource dataSource() {
        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
                .build();
    }*/

    @Bean()
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceUrl);
        dataSource.setDriverClassName(dataSourceDriverClass);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        /*if(transactionManager instanceof JtaTransactionManager) {
            sessionFactoryBean.setJtaTransactionManager(transactionManager);
        }*/
        sessionFactoryBean.setPackagesToScan(Constants.PACKAGE_TO_SCAN);
        sessionFactoryBean.setConfigLocation(new ClassPathResource("config/hibernate.cfg.xml"));
        return sessionFactoryBean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, LoadTimeWeaver loadTimeWeaver) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setLoadTimeWeaver(loadTimeWeaver);
        em.setPackagesToScan(Constants.PACKAGE_TO_SCAN);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        return properties;
    }
   /* @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setDefaultTimeout(Constants.DEFAULT_TRANSACTION_TIME_OUT);
        return transactionManager;
    }*/
 /*@Bean
   public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
       HibernateTransactionManager transactionManager = new HibernateTransactionManager();
       transactionManager.setDefaultTimeout(Constants.DEFAULT_TRANSACTION_TIME_OUT);
       transactionManager.setSessionFactory(sessionFactory);
       return transactionManager;
   }*/

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDefaultTimeout(transactionTimeout);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setRollbackOnCommitFailure(true);
        return transactionManager;
    }

    @Bean
    public AnnotationTransactionAspect transactionAspect(PlatformTransactionManager transactionManager) {
        AnnotationTransactionAspect transactionAspect = new AnnotationTransactionAspect();
        transactionAspect.setTransactionManager(transactionManager);
        return transactionAspect;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TransactionManagementConfigurer transactionManagementConfigurer(final PlatformTransactionManager transactionManager) {
        return new TransactionManagementConfigurer() {
            @Override
            public PlatformTransactionManager annotationDrivenTransactionManager() {
                return transactionManager;
            }
        };
    }
}
