package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.application.service.ForgottenTaskService;
import send.toyou.schedulertaskmanager.application.service.LaunchService;
import send.toyou.schedulertaskmanager.application.service.MessageService;
import send.toyou.schedulertaskmanager.application.service.TaskScheduledService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledForgottenTask;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;

@Service
@Slf4j
public class LaunchServiceImpl implements LaunchService {
    @Autowired
    private MessageService messageService;

    @Autowired
    private TaskScheduledService taskScheduledService;

    @Autowired
    private ForgottenTaskService forgottenTaskService;

    @Override
    public Mono<ScheduledTaskDto> launchScheduledTask(ScheduledTaskDto scheduledTaskDto) {
        var sentMessage = this.messageService.send(scheduledTaskDto.getIdTask(), scheduledTaskDto.getMessage(), scheduledTaskDto.getDestination());

        if (sentMessage) {
            log.info("Sent mesage with id: {}", scheduledTaskDto.getIdTask());
        } else {
            log.error("Message is not sent with id: {}", scheduledTaskDto.getIdTask());
        }

        return this.taskScheduledService.findTaskById(scheduledTaskDto.getIdTask())
                .doOnNext(task -> log.info("Task found: {}", task))
                .flatMap(taskScheduledService::registerLastExecution)
                .map(ScheduledTaskDto::fromScheduleTask);
    }

    @Override
    public Flux<ScheduledTaskDto> launchForgottenTasks() {
        return this.taskScheduledService.loadAllTasks()
                .flatMap(task -> {
                  final var scheduledForgottenTask = ScheduledForgottenTask.fromScheduledDto(task);

                  if (this.forgottenTaskService.isForgottenTask(scheduledForgottenTask)) {
                      log.info("Fogotten task: {}", task);
                      return this.launchScheduledTask(task);
                  }

                  return Mono.empty();
                });
    }
}
