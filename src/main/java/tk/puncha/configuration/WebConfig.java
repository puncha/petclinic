package tk.puncha.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  Properties exceptionMappings() {
    Properties mappings = new Properties();
    mappings.put("OopsException", "exception/oops");
    return mappings;
  }

  @Bean
  public HandlerExceptionResolver handlerExceptionResolver() {
    SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
    resolver.setDefaultErrorView("exception/default");
    resolver.setExceptionAttribute("exception");
    resolver.setExceptionMappings(exceptionMappings());
    return resolver;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    super.configureViewResolvers(registry);
    registry.jsp("/WEB-INF/jsp/", ".jsp");
  }

  @Override
  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
    exceptionResolvers.add(handlerExceptionResolver());
  }

  @Bean
  @Profile("hsqldb")
  public DataSource hsqldbDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
    dataSource.setUrl("jdbc:hsqldb:hsql://localhost/");
    return dataSource;
  }

  @Bean
  @Profile("mysql")
  public DataSource mysqlDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/petclinic?useSSL=false");
    dataSource.setUsername("root");
    dataSource.setPassword("sa");
    return dataSource;
  }

  @Bean
  @Profile("hibernate")
  public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return entityManagerFactoryBean;
  }

  @Bean
  public JpaTransactionManager txManager(DataSource dataSource) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setDataSource(dataSource);
    return jpaTransactionManager;
  }

  @Override
  public Validator getValidator() {
    LocalValidatorFactoryBean localValidator = new LocalValidatorFactoryBean();
    localValidator.setValidationMessageSource(validationMessageSource());
    return localValidator;
  }

  @Bean
  public MessageSource validationMessageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("validation");
    messageSource.setDefaultEncoding("utf8");
    return messageSource;
  }

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("validation");
    messageSource.setDefaultEncoding("utf8");
    return messageSource;
  }

}