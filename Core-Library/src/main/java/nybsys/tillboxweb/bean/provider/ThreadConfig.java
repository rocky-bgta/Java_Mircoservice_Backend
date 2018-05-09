/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 4/26/2018
 * Time: 8:58 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bean.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Spring_thread");
        executor.initialize();
        return executor;
    }
}
