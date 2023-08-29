package send.toyou.schedulertaskmanager.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class StreamConfiguration {
    @Bean
    public Function<Flux<String>, Flux<String>> newTaskToSchedule() {
        return stringFlux -> stringFlux;
    }

    @Bean
    public Function<Flux<String>, Flux<String>> updateJob() {
        return stringFlux -> stringFlux;
    }
    @Bean
    public Function<Flux<String>, Flux<String>> duplicateTask() {
        return stringFlux -> stringFlux;
    }
    @Bean
    public Function<Flux<String>, Flux<String>> launcherTasks() {
        return stringFlux -> stringFlux;
    }

    @Bean
    public Function<Flux<String>, Flux<String>> deleteTask() {
        return stringFlux -> stringFlux;
    }
}
