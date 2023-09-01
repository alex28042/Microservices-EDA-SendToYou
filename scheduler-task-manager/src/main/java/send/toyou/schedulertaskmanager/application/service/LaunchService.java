package send.toyou.schedulertaskmanager.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;

public interface LaunchService {


    Mono<ScheduledTaskDto> launchScheduledTask(ScheduledTaskDto scheduledTaskDto);

    Flux<ScheduledTaskDto> launchForgottenTasks();
}
