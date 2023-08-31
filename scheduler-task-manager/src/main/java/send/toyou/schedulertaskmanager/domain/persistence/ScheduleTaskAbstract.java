package send.toyou.schedulertaskmanager.domain.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public abstract class ScheduleTaskAbstract {
    private String idTask;
    private String message;
    private String cron;
    private String destination;
    private String description;
    private String taskCreationDate;
    private String lastExecutionDate;
}
