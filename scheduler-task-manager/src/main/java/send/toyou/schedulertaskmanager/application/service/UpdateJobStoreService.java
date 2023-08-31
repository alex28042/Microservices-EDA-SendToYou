package send.toyou.schedulertaskmanager.application.service;

import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;

public interface UpdateJobStoreService {
    ScheduledTaskDto updateJobStore(ScheduledTaskDto scheduledTaskDto);
    ScheduledTaskDto deletingJobstore(ScheduledTaskDto scheduledTaskDto);

}
