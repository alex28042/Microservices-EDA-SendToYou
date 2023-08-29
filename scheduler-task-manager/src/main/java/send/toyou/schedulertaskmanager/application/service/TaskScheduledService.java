package send.toyou.schedulertaskmanager.application.service;

import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;

public interface TaskScheduledService {
    Mono<ScheduledTaskDto> saveNewTask(ScheduledTaskDto schduledTaskDto);
}
