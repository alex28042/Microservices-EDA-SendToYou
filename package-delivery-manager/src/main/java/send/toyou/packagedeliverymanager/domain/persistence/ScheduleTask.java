package send.toyou.packagedeliverymanager.domain.persistence;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Table(value = "scheduled_task")
public class ScheduleTask extends ScheduleTaskAbstract {
    public ScheduleTask(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }
}
