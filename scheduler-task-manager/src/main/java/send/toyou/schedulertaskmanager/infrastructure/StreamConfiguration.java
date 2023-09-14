package send.toyou.schedulertaskmanager.infrastructure;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import send.toyou.schedulertaskmanager.application.processors.*;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;
import send.toyou.schedulertaskmanager.domain.events.NewDeleteTaskEvent;
import send.toyou.schedulertaskmanager.domain.events.NewScheduledTaskEvent;
import send.toyou.schedulertaskmanager.domain.events.UpdateJobStoreEvent;

import java.util.function.Function;

@Configuration
public class StreamConfiguration {
    @Bean
    public Function<Flux<NewScheduledTaskEvent>, Flux<UpdateJobStoreEvent>> newTaskToSchedule(final NewTaskToScheduleProcessor newTaskToScheduleProcessor) {
        return newTaskToScheduleProcessor::process;
    }

    @Bean
    public Function<Flux<UpdateJobStoreEvent>, Flux<ScheduledTaskDto>> updateJob(final UpdateJobProcessor updateJobProcessor) {
        return updateJobProcessor::process;
    }
    @Bean
    public Function<KStream<String, JobTriggerEvent>, KStream<String, JobTriggerEvent>> duplicateTask(final DuplicateProcessor duplicateProcessor) {
        return duplicateProcessor::process;
    }
    @Bean
    public Function<Flux<JobTriggerEvent>, Flux<ScheduledTaskDto>> launcherTasks(final LauncherTaskProcessor launcherTaskProcessor) {
        return launcherTaskProcessor::process;
    }

    @Bean
    public Function<Flux<NewDeleteTaskEvent>, Flux<Object>> deleteTask(final DeleteTaskProcessor deleteTaskProcessor) {
        return deleteTaskProcessor::process;
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
