package send.toyou.schedulertaskmanager.application.service;

import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;

public interface TaskScheduledService {
    Mono<ScheduledTaskDto> saveNewTask(ScheduledTaskDto schduledTaskDto);
    Mono<ScheduleTask> findTaskById(String taskId);
    Mono<ScheduleTask> registerLastExecution(ScheduleTask scheduleTask);
}
