package nybsys.tillboxweb.dbConfig;

import nybsys.tillboxweb.constant.TillBoxDbConstant;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
//@ComponentScan({ "com.nybsys.tillboxweb.dao.repositories" })
public class PersistenceConfig {

    @Autowired
    private Environment env;

    //@Scope("prototype")


    // business specific DB configuration

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(
                new String[]{
                        TillBoxDbConstant.BUSINESS_DB_ENTITY_PATH,
                        TillBoxDbConstant.CORE_DB_ENTITY_PATH,
                        TillBoxDbConstant.HISTORY_ENTITY_PATH});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    @Lazy
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DataSource dataSource(String dataBaseName) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(TillBoxDbConstant.DB_DRIVER_CLASS);
        dataSource.setUrl(TillBoxDbConstant.DB_URL + dataBaseName);
        dataSource.setUsername(TillBoxDbConstant.DB_USER_NAME);
        dataSource.setPassword(TillBoxDbConstant.DB_USER_PASSWORD);
        return dataSource;
    }

    // business specific DB configuration

    @Bean
    @Lazy
    //@Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public LocalSessionFactoryBean defaultSessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(defaultDataSource());
        sessionFactory.setPackagesToScan(
                new String[]{
                        TillBoxDbConstant.DEFAULT_DB_ENTITY_PATH,
                        TillBoxDbConstant.HISTORY_ENTITY_PATH});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    //@Lazy
    //@Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public DataSource defaultDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(TillBoxDbConstant.DB_DRIVER_CLASS);
        dataSource.setUrl(TillBoxDbConstant.DB_URL + TillBoxDbConstant.DEFAULT_DATABASE);
        dataSource.setUsername(TillBoxDbConstant.DB_USER_NAME);
        dataSource.setPassword(TillBoxDbConstant.DB_USER_PASSWORD);
        return dataSource;
    }


  /*
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource defaultDataSource)
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(defaultDataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource(env.getProperty("init-scripts")));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(Boolean.parseBoolean(env.getProperty("init-dbConfig",
                "false")));
        return dataSourceInitializer;
    }
    */


    /*

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(TillBoxDbConstant.DB_USER_NAME);
        dataSource.setPassword(TillBoxDbConstant.DB_USER_PASSWORD);
        dataSource.setJdbcUrl(TillBoxDbConstant.DB_URL + TillBoxDbConstant.DEFAULT_DATABASE);
        dataSource.setDataSourceClassName(TillBoxDbConstant.DB_DRIVER_CLASS);
        //dataSource.setMinimumIdle(2);
        //dataSource.setMaximumPoolSize(5);
        return dataSource;
    }*/

   /* public SessionFactory sessionFactory() {
        SessionFactory sessionFactory;
        Configuration configuration = new org.hibernate.cfg.Configuration()
                .setProperty(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/course")
                .setProperty(AvailableSettings.USER, "postgres")
                .setProperty(AvailableSettings.PASS, "password")
                .setProperty(AvailableSettings.DIALECT, PostgreSQL95Dialect.class.getName())
                .setProperty(AvailableSettings.SHOW_SQL, String.valueOf(true))
                .setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
                //.addClass(Course.class);
        return sessionFactory = configuration.buildSessionFactory();
    }*/

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    //@Lazy
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    //@Lazy
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


  /*
  //properties.put("hibernate.current_session_context_class", "org.hibernate.context.internal.ThreadLocalSessionContext");
  hibernate.c3p0.min_size=5
    hibernate.c3p0.max_size=20
    hibernate.c3p0.timeout=1800
    hibernate.c3p0.max_statements=50*/

    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", TillBoxDbConstant.UPDATE_TABLE);
        //properties.put("hibernate.transaction.flush_before_completion", "true");
        //properties.put("hibernate.transaction.auto_close_session", "true");

        properties.put("hibernate.dialect", TillBoxDbConstant.DB_DIALECT);
        properties.put("hibernate.globally_quoted_identifiers", "true");
        properties.put("hibernate.c3p0.min_size", "1");
        properties.put("hibernate.c3p0.max_size", "1");
        properties.put("hibernate.c3p0.max_statements", "50");
        properties.put("hibernate.c3p0.acquire_increment", "1");
        properties.put("hibernate.c3p0.idle_test_period", "300");
        properties.put("hibernate.c3p0.timeout", "1800");
        //properties.put("hibernate.current_session_context_class", "jta");

        //properties.put("hibernate.format_sql",true);
        //properties.put("hibernate.use_sql_comments",true);

        return properties;
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    String stringProvider(String name) {
        return name;
    }
}
