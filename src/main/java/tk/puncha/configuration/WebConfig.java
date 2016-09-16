package tk.puncha.configuration;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import tk.puncha.viewResolvers.MyErrorViewResolver;
import tk.puncha.viewResolvers.AtomFeedViewResolver;
import tk.puncha.viewResolvers.ExcelViewResolver;
import tk.puncha.viewResolvers.JsonViewResolver;
import tk.puncha.viewResolvers.PdfViewResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/ng1/**").addResourceLocations("/resources/ng1/");
    registry.addResourceHandler("/ng2/**").addResourceLocations("/resources/ng2/");
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    super.extendMessageConverters(converters);
  }


  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer
        .defaultContentType(MediaType.TEXT_HTML)
        .mediaType("html", MediaType.TEXT_HTML)
        .mediaType("xml", MediaType.APPLICATION_XML)
        .mediaType("json", MediaType.APPLICATION_JSON)
        .favorPathExtension(true)
        .favorParameter(true)
        .parameterName("mediaType")
        .ignoreAcceptHeader(false);
  }

  @Profile("webApp")
  @Bean
  public XmlViewResolver xmlViewResolver() {
    return new XmlViewResolver();
  }

  @Bean
  public JsonViewResolver jsonViewResolver() {
    return new JsonViewResolver();
  }

  @Bean
  public ExcelViewResolver excelViewResolver() {
    return new ExcelViewResolver();
  }

  @Bean
  public PdfViewResolver pdfViewResolver() {
    return new PdfViewResolver();
  }

  @Bean
  public AtomFeedViewResolver atomFeedViewResolver() {
    return new AtomFeedViewResolver();
  }

  @Bean
  public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
    Properties mappings = new Properties();
    mappings.put("OopsException", "exception/oops");

    SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
    resolver.setDefaultErrorView("exception/default");
    resolver.setExceptionAttribute("exception");
    resolver.setOrder(Integer.MAX_VALUE);
    resolver.setExceptionMappings(mappings);
    return resolver;
  }

  @Bean
  public ErrorViewResolver errorViewResolver() {
    return new MyErrorViewResolver();
  }

  @Bean
  @Profile("embeddedHsqldb")
  public DataSource embeddedHsqldb() {
    return new EmbeddedDatabaseBuilder()
        .generateUniqueName(true)
        .setType(EmbeddedDatabaseType.HSQL)
        .setScriptEncoding("UTF-8")
        .ignoreFailedDrops(true)
        .addScript("schema-hsqldb.sql")
        .addScripts("data-hsqldb.sql")
        .build();
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