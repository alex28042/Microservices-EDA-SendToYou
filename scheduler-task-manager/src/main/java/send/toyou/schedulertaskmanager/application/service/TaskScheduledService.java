package send.toyou.schedulertaskmanager.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;

public interface TaskScheduledService {
    Flux<ScheduledTaskDto> loadAllTasks();
    Mono<ScheduledTaskDto> saveNewTask(ScheduledTaskDto schduledTaskDto);
    Mono<ScheduleTask> findTaskById(String taskId);
    Mono<ScheduleTask> registerLastExecution(ScheduleTask scheduleTask);
}
