/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 01-Jan-18
 * Time: 2:43 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.bean.provider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
@PropertySource("classpath:/application.properties")
public class EnvironmentVariables {

    @Autowired
    private Environment environment;

   /* public DataSource mySqlDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }*/


  /*  @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);



        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        //props.put("mail.debug", "true");
        mailSender.setUsername("tillboxweb@gmail.com");
        mailSender.setPassword("tillboxweb123");

        mailSender.setJavaMailProperties(props);
        return mailSender;
    }*/
}
