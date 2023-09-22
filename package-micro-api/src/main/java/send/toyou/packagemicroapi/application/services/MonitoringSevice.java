package send.toyou.packagemicroapi.application.services;

public interface MonitoringSevice {
    void sendValidationErrors(Object object, String errorFrom, String description);
}
