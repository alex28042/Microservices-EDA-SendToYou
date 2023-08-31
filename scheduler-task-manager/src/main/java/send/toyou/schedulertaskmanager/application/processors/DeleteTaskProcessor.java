package send.toyou.schedulertaskmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.application.service.UpdateJobStoreService;
import send.toyou.schedulertaskmanager.application.service.impl.UpdateJobStoreServiceImpl;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.NewDeleteTaskEvent;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.repositories.ScheduledTaskRepository;

@Component
@Slf4j
public class DeleteTaskProcessor {
    @Autowired
    private UpdateJobStoreService updateJobStoreService;

    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    public Flux<Object> process(final Flux<NewDeleteTaskEvent> inbound) {
        return inbound
                .flatMap(task -> this.scheduledTaskRepository.findById(task.getIdTask())
                        .switchIfEmpty(Mono.just(new ScheduleTask())))
                .map(ScheduledTaskDto::fromScheduleTask)
                .map(updateJobStoreService::deletingJobstore)
                .flatMap(task -> this.scheduledTaskRepository.deleteById(task.getIdTask()))
                .onErrorContinue(this::handleError)
                .map(task -> Mono.empty());
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error in DeleteTaskProcessor: {}", object, throwable);
    }
}
