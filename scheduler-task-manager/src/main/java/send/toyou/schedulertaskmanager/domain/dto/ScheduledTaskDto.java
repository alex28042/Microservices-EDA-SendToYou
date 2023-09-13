package send.toyou.schedulertaskmanager.domain.dto;

import lombok.*;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;
import send.toyou.schedulertaskmanager.domain.events.UpdateJobStoreEvent;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTaskAbstract;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class ScheduledTaskDto extends ScheduleTaskAbstract {
    public ScheduledTaskDto(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }

    public static ScheduledTaskDto fromScheduleTask(ScheduleTask scheduleTask) {
        return new ScheduledTaskDto(
                scheduleTask.getIdTask(),
                scheduleTask.getMessage(),
                scheduleTask.getCron(),
                scheduleTask.getDestination(),
                scheduleTask.getDescription(),
                scheduleTask.getTaskCreationDate(),
                scheduleTask.getLastExecutionDate()
        );
    }

    public static ScheduledTaskDto fromUpdateJobStoreEvent(UpdateJobStoreEvent updateJobStoreEvent) {
        return new ScheduledTaskDto(
                updateJobStoreEvent.getIdTask(),
                updateJobStoreEvent.getMessage(),
                updateJobStoreEvent.getCron(),
                updateJobStoreEvent.getDestination(),
                updateJobStoreEvent.getDescription(),
                updateJobStoreEvent.getTaskCreationDate(),
                updateJobStoreEvent.getLastExecutionDate()
        );
    }

    public static ScheduledTaskDto fromJobTriggerEvent(JobTriggerEvent jobTriggerEvent) {
        return new ScheduledTaskDto(
                jobTriggerEvent.getIdTask(),
                jobTriggerEvent.getMessage(),
                jobTriggerEvent.getCron(),
                jobTriggerEvent.getDestination(),
                jobTriggerEvent.getDescription(),
                jobTriggerEvent.getTaskCreationDate(),
                jobTriggerEvent.getLastExecutionDate()
        );
    }
}
