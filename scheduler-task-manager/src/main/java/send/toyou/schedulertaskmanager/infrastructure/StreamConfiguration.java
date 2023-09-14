package send.toyou.schedulertaskmanager.infrastructure;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
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

    @Bean
    public StoreBuilder<KeyValueStore<String, Short>> jobTriggersStore() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(DuplicateProcessor.STORE_NAME),
                Serdes.String(),
                Serdes.Short()
        );
    }
}
