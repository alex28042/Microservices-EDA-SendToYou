package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.application.service.TaskScheduledService;
import send.toyou.schedulertaskmanager.application.utils.MiscUtils;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.repositories.ScheduledTaskRepository;

@Service
@Slf4j
public class TaskScheduledServiceImpl implements TaskScheduledService {
    @Autowired
    private ScheduledTaskRepository taskRepository;

    @Override
    public Mono<ScheduledTaskDto> saveNewTask(ScheduledTaskDto schduledTaskDto) {
        log.info("Save Task Service: {}", schduledTaskDto);
        return MiscUtils.isValidatedNewTask(schduledTaskDto) ?
                this.taskRepository.save(ScheduleTask.fromScheduledTaskDto(schduledTaskDto))
                        .map(ScheduledTaskDto::fromScheduleTask)
                        .doOnNext(task -> log.info("Task saved: {}", task)) :
                Mono.empty();
    }
}
