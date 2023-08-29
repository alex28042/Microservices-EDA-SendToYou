package send.toyou.schedulertaskmanager.domain.events;

import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTaskAbstract;

public class NewScheduledTaskEvent extends ScheduleTaskAbstract {
    public NewScheduledTaskEvent(String idTask, String message, String cron, String destination, String description, String taskCreationDate, String lastExecutionDate) {
        super(idTask, message, cron, destination, description, taskCreationDate, lastExecutionDate);
    }
}
