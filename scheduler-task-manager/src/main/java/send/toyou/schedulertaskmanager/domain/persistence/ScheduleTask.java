package send.toyou.schedulertaskmanager.domain.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.NewScheduledTaskEvent;

@Getter
@Setter
@NoArgsConstructor
@Table(value = "scheduled_task")
public class ScheduleTask extends ScheduleTaskAbstract {
    public ScheduleTask(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }

    public static ScheduleTask fromNewScheduledTaskEvent(NewScheduledTaskEvent newScheduledTaskEvent) {
        return new ScheduleTask(
                newScheduledTaskEvent.getIdTask(),
                newScheduledTaskEvent.getMessage(),
                newScheduledTaskEvent.getCron(),
                newScheduledTaskEvent.getDestination(),
                newScheduledTaskEvent.getDescription(),
                newScheduledTaskEvent.getTaskCreationDate(),
                newScheduledTaskEvent.getLastExecutionDate()
        );
    }

    public static ScheduleTask fromScheduledTaskDto(ScheduledTaskDto scheduledTaskDto) {
        return new ScheduleTask(
                scheduledTaskDto.getIdTask(),
                scheduledTaskDto.getMessage(),
                scheduledTaskDto.getCron(),
                scheduledTaskDto.getDestination(),
                scheduledTaskDto.getDescription(),
                scheduledTaskDto.getTaskCreationDate(),
                scheduledTaskDto.getLastExecutionDate()
        );
    }
}
