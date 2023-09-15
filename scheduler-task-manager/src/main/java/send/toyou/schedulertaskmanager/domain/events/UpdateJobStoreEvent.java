package send.toyou.schedulertaskmanager.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTaskAbstract;

@NoArgsConstructor
@Getter
@Setter
public class UpdateJobStoreEvent extends ScheduleTaskAbstract {

    public UpdateJobStoreEvent(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }
    public static UpdateJobStoreEvent fromScheduleTaskDto(ScheduledTaskDto scheduledTaskDto) {
        return new UpdateJobStoreEvent(
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
