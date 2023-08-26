package send.toyou.packagemicroapi.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import send.toyou.packagemicroapi.application.MonitoringSevice;
import send.toyou.packagemicroapi.domain.events.ErrorEvent;
import send.toyou.packagemicroapi.domain.constants.MonitoringConstants;

@Service
public class MonitoringServiceImpl implements MonitoringSevice {
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
