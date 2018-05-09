package nybsys.tillboxweb.Utils;

//import com.nybsys.tillboxweb.dbConfig.PersistenceConfig;


import nybsys.tillboxweb.broker.client.WorkersStatus;
import nybsys.tillboxweb.constant.TillBoxDbConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CyclicBarrier;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

//@Component
public final class TillBoxUtils {

    public static final Runtime runtime = Runtime.getRuntime();

    //@Autowired
    //public static JavaMailSender emailSender;
    private static final Logger log = LoggerFactory.getLogger(TillBoxUtils.class);
    private static final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private static final Properties props = mailSender.getJavaMailProperties();

    static {
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.debug", "true");
        mailSender.setUsername("tillboxweb@gmail.com");
        mailSender.setPassword("tillboxweb123");
        mailSender.setJavaMailProperties(props);
    }

    public static void removeExistingBean(String beanId, AnnotationConfigApplicationContext applicationContext) {

        try {
            AutowireCapableBeanFactory factory =
                    applicationContext.getAutowireCapableBeanFactory();
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
            registry.removeBeanDefinition(beanId);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            throw e;
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
            throw e;
        }


       /* ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        beanFactory.registerSingleton(bean.getClass().getCanonicalName(), bean);
*/
        //create newBeanObj through GenericBeanDefinition

        //registry.registerBeanDefinition(beanId, newBeanObj);


       /* GenericBeanDefinition myBeanDefinition = new GenericBeanDefinition();
        myBeanDefinition.setBeanClass(MyDynamicBean.class);
        myBeanDefinition.setScope(SCOPE_PROTOTYPE);
        myBeanDefinition.setPropertyValues(getMutableProperties(dynamicPropertyPrefix));
        registry.registerBeanDefinition(dynamicBeanId, myBeanDefinition);*/
    }

    public static void changeDataBase(AnnotationConfigApplicationContext applicationContext, DataSource dataSource, SessionFactory sessionFactory) {
        try {
            AutowireCapableBeanFactory factory =
                    applicationContext.getAutowireCapableBeanFactory();
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
            registry.removeBeanDefinition("defaultDataSource");
            registry.removeBeanDefinition("defaultSessionFactory");

            //factory.initializeBean(dataSource,"defaultDataSource");
            //factory.initializeBean(sessionFactory,"defaultSessionFactory");

            applicationContext.getBeanFactory().registerSingleton("defaultDataSource", dataSource);
            applicationContext.getBeanFactory().registerSingleton("defaultSessionFactory", sessionFactory);
            // applicationContext.refresh();
            //applicationContext.register(PersistenceConfig.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public static int getAvailableCpuCore() {
        return runtime.availableProcessors();
    }

    public static <T extends Object> T castValue(String type, Object inputValue) {
        Object result = null;
        try {
            switch (type) {
                case "java.lang.Integer":
                    result = Integer.parseInt(inputValue.toString());
                    break;
                case "java.lang.Double":
                    result = Double.parseDouble(inputValue.toString());
                    break;
                case "java.lang.Float":
                    result = Float.parseFloat(inputValue.toString());
                    break;
                case "java.lang.Boolean":
                    result = Boolean.parseBoolean(inputValue.toString());
                    break;
                case "java.lang.Long":
                    result = Long.parseLong(inputValue.toString());
                    break;
                case "java.util.Date":
                    result = (Date) inputValue;
                    break;
                case "java.lang.String":
                    //result = "'" + inputValue + "'";
                    result = (String) inputValue;
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return (T) result;
    }

    public static boolean isDatabaseExists(String dbName) throws SQLException {
        boolean isDatabaseExists = false;
        String url = TillBoxDbConstant.DB_URL;
        //String JDBC_DRIVER = TillBoxDbConstant.DB_DRIVER_CLASS;
        Connection conn = null;
        Statement stmt = null;
        String query = "SELECT count(*) FROM pg_database WHERE datname = '" + dbName + "'";
        try {
            //Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url, TillBoxDbConstant.DB_USER_NAME, TillBoxDbConstant.DB_USER_PASSWORD);
            System.out.println("Connected database successfully...");

            stmt = conn.createStatement();

            System.out.println("Getting Database ...");
            ResultSet resultSet = stmt.executeQuery(query);
            resultSet.next();
            int a = resultSet.getInt(1);
            if (resultSet.getInt(1) > 0) {
                System.out.println("Get Database successfully...");
                isDatabaseExists = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
        return isDatabaseExists;
    }

    public static boolean createDatabase(String dbName, String dateTime) throws SQLException {
        String buildDbName = null, buildDateTime = null;
        try {
            if (dateTime == null) {
                return createDatabase(dbName);
            } else {
                //2016/11/16 12:08:43
                buildDateTime = StringUtils.replace(dateTime, " ", "_T").toString();
                buildDateTime = StringUtils.replace(buildDateTime, "/", "_").toString();
                buildDateTime = StringUtils.replace(buildDateTime, ":", "_").toString();
                buildDbName = dbName + "_D" + buildDateTime;
                return createDatabase(buildDbName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static boolean createDatabase(String dbName) throws SQLException {
        boolean isDatabaseCreated = false;
        String url = TillBoxDbConstant.DB_URL;
        //String JDBC_DRIVER = TillBoxDbConstant.DB_DRIVER_CLASS;
        Connection conn = null;
        Statement stmt = null;
        String query = "CREATE DATABASE " + dbName;
        try {
            //Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url, TillBoxDbConstant.DB_USER_NAME, TillBoxDbConstant.DB_USER_PASSWORD);
            System.out.println("Connected database successfully...");

            stmt = conn.createStatement();

            System.out.println("Creating Database ...");
            stmt.executeUpdate(query);
            System.out.println("Database created successfully...");
            isDatabaseCreated = true;


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        }
        return isDatabaseCreated;
    }

    public static String getDbName(String userID) {
        String dbName = null;
        try {
            dbName = getDbName(null, userID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbName;
    }

    public static String getDbName(String businessID, String userID) {
        String buildString = null;
        try {

            buildString = StringUtils.replace(userID, "@", "_").toString();
            buildString = StringUtils.replace(buildString, ".", "_").toString();
            if (businessID != null)
                buildString = businessID + "_" + buildString;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return buildString;
    }

    public static String getDate() {
        String result = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println(dateTimeFormatter.format(localDateTime)); //2016/11/16
            result = dateTimeFormatter.format(localDateTime).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public static String getDateTime() {
        String result = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            System.out.println(dateTimeFormatter.format(localDateTime)); //2016/11/16 12:08:43
            result = dateTimeFormatter.format(localDateTime).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public static Date firstDate(int year, int month) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.of(year, month, 1);
            date = format.parse(localDate.toString());
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date lastDate(int year, int month) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate localDate = LocalDate.of(year, month, 1);
            LocalDate end = localDate.with(lastDayOfMonth());
            date = format.parse(end.toString());
            System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static CyclicBarrier getBarrier(int numberOfRequest, Object lock) {
        CyclicBarrier barrier = new CyclicBarrier(numberOfRequest, new WorkersStatus(lock));
        return barrier;
    }


    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


}
