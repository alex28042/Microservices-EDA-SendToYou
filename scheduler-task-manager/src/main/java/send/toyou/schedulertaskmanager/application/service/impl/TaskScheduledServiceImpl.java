package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.schedulertaskmanager.application.service.TaskScheduledService;
import send.toyou.schedulertaskmanager.application.service.UpdateJobStoreService;
import send.toyou.schedulertaskmanager.application.utils.MiscUtils;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.repositories.ScheduledTaskRepository;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Slf4j
public class TaskScheduledServiceImpl implements TaskScheduledService {
    @Autowired
    private ScheduledTaskRepository taskRepository;

    @Autowired
    private UpdateJobStoreService updateJobStoreService;

    @Override
    public Flux<ScheduledTaskDto> loadAllTasks() {
        return this.taskRepository.findAll()
                .map(ScheduledTaskDto::fromScheduleTask)
                .filter(this::hasFutureExecution)
                .doOnNext(given -> log.info("Loaded task: {}", given))
                .map(updateJobStoreService::updateJobStore);
    }

    @SneakyThrows
    private boolean hasFutureExecution(ScheduledTaskDto scheduledTaskDto) {
        return MiscUtils.getNextExecution(scheduledTaskDto.getCron()) != null;
    }

    @Override
    public Mono<ScheduledTaskDto> saveNewTask(ScheduledTaskDto schduledTaskDto) {
        log.info("Save Task Service: {}", schduledTaskDto);
        return MiscUtils.isValidatedNewTask(schduledTaskDto) ?
                this.taskRepository.save(ScheduleTask.fromScheduledTaskDto(schduledTaskDto))
                        .map(ScheduledTaskDto::fromScheduleTask)
                        .doOnNext(task -> log.info("Task saved: {}", task)) :
                Mono.empty();
    }

    @Override
    public Mono<ScheduleTask> findTaskById(String taskId) {
        return this.taskRepository.findById(taskId);
    }

    public Mono<ScheduleTask> registerLastExecution(ScheduleTask scheduleTask) {
        scheduleTask.setLastExecutionDate(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now(ZoneId.of(ZoneOffset.UTC.getId()))));

        return this.taskRepository.save(scheduleTask)
                .doOnNext(task -> log.info("task saved: {}", scheduleTask));
    }

}
