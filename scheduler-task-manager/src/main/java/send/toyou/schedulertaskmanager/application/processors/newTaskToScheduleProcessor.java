package send.toyou.schedulertaskmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.schedulertaskmanager.application.service.impl.TaskScheduledServiceImpl;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.NewScheduledTaskEvent;
import send.toyou.schedulertaskmanager.domain.events.UpdateJobStoreEvent;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.repositories.ScheduledTaskRepository;

@Component
@Slf4j
public class newTaskToScheduleProcessor {
    @Autowired
    private ScheduledTaskRepository taskRepository;

    @Autowired
    private TaskScheduledServiceImpl taskScheduledService;

    public Flux<UpdateJobStoreEvent> process(final Flux<NewScheduledTaskEvent> input) {
        return input
                .doOnNext(newScheduledTaskEvent -> log.info("New Task: {}", newScheduledTaskEvent))
                .map(ScheduleTask::fromNewScheduledTaskEvent)
                .flatMap(scheduleTask -> this.taskRepository.findById(scheduleTask.getIdTask()))
                .map(ScheduledTaskDto::fromScheduleTask)
                .flatMap(taskScheduledService::saveNewTask)
                .map(UpdateJobStoreEvent::fromScheduleTaskDto)
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error new Task");
    }
}
