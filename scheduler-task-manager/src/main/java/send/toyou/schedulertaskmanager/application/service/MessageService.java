package send.toyou.schedulertaskmanager.application.service;

public interface MessageService {
    boolean send(String key, Object message, String destination);
}
