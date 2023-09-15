package send.toyou.schedulertaskmanager.application.service;

import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;

public interface TriggerService {
    void processJob(ScheduledTaskDto scheduledTaskDto);
}
