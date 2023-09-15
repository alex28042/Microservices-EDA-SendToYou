package send.toyou.schedulertaskmanager.application.service;

import send.toyou.schedulertaskmanager.domain.dto.ScheduledForgottenTask;

public interface ForgottenTaskService {
    boolean isForgottenTask(ScheduledForgottenTask scheduledForgottenTask);
}
