package send.toyou.packagedeliverymanager.domain.persistence;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class ScheduleTaskAbstract {
    private String idTask;
    private String message;
    private String cron;
    private String destination;
    private String description;
    private String taskCreationDate;
    private String lastExecutionDate;
}
