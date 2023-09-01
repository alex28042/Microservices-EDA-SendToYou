package send.toyou.packagedeliverymanager.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import send.toyou.packagedeliverymanager.application.service.MonitoringService;
import send.toyou.packagedeliverymanager.domain.constants.MonitoringConstants;
import send.toyou.packagedeliverymanager.domain.events.ErrorEvent;

@Service
public class MonitoringServiceImpl implements MonitoringService {
    @Autowired
    private StreamBridge streamBridge;
    @Override
    public void sendValidationErrors(Object object, String errorFrom, String description) {
        var errorEvent = new ErrorEvent();

        errorEvent.setObject(object);
        errorEvent.setErrorFrom(errorFrom);
        errorEvent.setDescription(description);

        this.streamBridge.send(MonitoringConstants.MONITORING_BINDER, errorEvent);
    }
}
