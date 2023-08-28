package send.toyou.packagedeliverymanager.application.service;

public interface MonitoringService {
    void sendValidationErrors(Object object, String errorFrom, String description);
}
