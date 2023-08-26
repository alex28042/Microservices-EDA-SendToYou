package send.toyou.packagemicroapi.application.useCases;

public interface MonitoringSevice {
    void sendValidationErrors(Object object, String errorFrom, String description);
}
