package send.toyou.packagedeliverymanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import send.toyou.packagedeliverymanager.domain.persistence.ScheduleTaskAbstract;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class NewScheduledTaskEvent extends ScheduleTaskAbstract {
    public NewScheduledTaskEvent(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }
}
