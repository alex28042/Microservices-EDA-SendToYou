package send.toyou.packagedeliverymanager.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import send.toyou.packagedeliverymanager.application.service.impl.MonitoringServiceImpl;
import send.toyou.packagedeliverymanager.domain.constants.MonitoringConstants;
import send.toyou.packagedeliverymanager.domain.events.ErrorEvent;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.NewScheduledTaskEvent;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class MonitoringServiceImplTest {
    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Autowired
    private OutputDestination output;

    private final Long TIMEOUT = 10000L;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void testMonitoringPackageEvent() {
        var pack = new NewPackageEvent();
        pack.setId("111");
        pack.setName("hola");

        var errorEvent = new ErrorEvent();
        errorEvent.setErrorFrom(MonitoringConstants.MONITORING_FROM_ERROR);
        errorEvent.setObject(pack);
        errorEvent.setDescription("error package id: " + pack.getId());

        this.monitoringService.sendValidationErrors(errorEvent.getObject(), errorEvent.getErrorFrom(), errorEvent.getDescription());

        Message<byte[]> errorResponse = output.receive(TIMEOUT, "sendtoyou.ingestion-errors");

        final var output = objectMapper.readValue(errorResponse.getPayload(), ErrorEvent.class);

        Assertions.assertThat(output).isNotNull();
    }

    @SneakyThrows
    @Test
    void testMonitoringNewScheduledEvent() {
        var pack = new NewScheduledTaskEvent();
        pack.setIdTask("111");
        pack.setDescription("hola");

        var errorEvent = new ErrorEvent();
        errorEvent.setErrorFrom(MonitoringConstants.MONITORING_FROM_ERROR);
        errorEvent.setObject(pack);
        errorEvent.setDescription("error package id: " + pack.getIdTask());

        this.monitoringService.sendValidationErrors(errorEvent.getObject(), errorEvent.getErrorFrom(), errorEvent.getDescription());

        Message<byte[]> errorResponse = output.receive(TIMEOUT, "sendtoyou.ingestion-errors");

        final var output = objectMapper.readValue(errorResponse.getPayload(), ErrorEvent.class);

        Assertions.assertThat(output).isNotNull();

    }
}
