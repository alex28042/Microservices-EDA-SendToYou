package send.toyou.schedulertaskmanager.infrastructure;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;

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
    public Function<KStream<String, JobTriggerEvent>, KStream<String, JobTriggerEvent>> duplicateTask() {
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
