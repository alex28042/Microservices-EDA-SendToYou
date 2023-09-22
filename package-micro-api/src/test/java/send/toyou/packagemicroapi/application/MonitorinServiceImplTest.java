package send.toyou.packagemicroapi.application;

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
import send.toyou.packagemicroapi.application.services.impl.MonitoringServiceImpl;
import send.toyou.packagemicroapi.domain.constants.MonitoringConstants;
import send.toyou.packagemicroapi.domain.events.ErrorEvent;
import send.toyou.packagemicroapi.domain.persistence.Package;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class MonitorinServiceImplTest {
    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Autowired
    private OutputDestination output;

    private final Long TIMEOUT = 10000L;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void testMonitoring() {
        var pack = new Package();
        pack.setId("111");
        pack.setName("hola");

        var errorEvent = new ErrorEvent();
        errorEvent.setErrorFrom(MonitoringConstants.MONITORING_FROM_ERROR);
        errorEvent.setObject(pack);
        errorEvent.setDescription("error package id: " + pack.getId());

        this.monitoringService.sendValidationErrors(errorEvent.getObject(), errorEvent.getErrorFrom(), errorEvent.getDescription());

        Message<byte[]> errorResponse = output.receive(TIMEOUT, "sendtoyou.api-errors");

        final var output = objectMapper.readValue(errorResponse.getPayload(), ErrorEvent.class);

        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output.getDescription()).contains(pack.getId());
    }
}
