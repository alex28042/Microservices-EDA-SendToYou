package send.toyou.packagedeliverymanager.application.processors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactory;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.events.NewScheduledTaskEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class PackageProcessedTest {
    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private InputDestination inputDestination;

    private final int TIME_OUT = 100;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ConnectionFactory connectionFactory;

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript))
                .block();
    }

    @BeforeEach
    void init(@Value("classpath:/schema.sql") Resource schema) {
        this.executeScriptBlocking(schema);
    }

    @AfterEach
    void after(@Value("classpath:/clear.sql") Resource clear) {
        this.executeScriptBlocking(clear);
    }
    @SneakyThrows
    @Test
    void processTest(@Value("classpath:/insert-data.sql") Resource insert) {
        executeScriptBlocking(insert);

        var packageEvent = new PackageProcessedEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("2");
        packageEvent.setStatus(PackageStatusEnum.PROCESSING.name());

        MessageBuilder<PackageProcessedEvent> packageEventMessageBuilder = MessageBuilder.withPayload(packageEvent);

        this.inputDestination.send(packageEventMessageBuilder.build(), "sendtoyou.package-processed-event");

        Message<byte[]> errorResponse = outputDestination.receive(TIME_OUT, "sendtoyou.scheduler-new-task-event");

        final var output = objectMapper.readValue(errorResponse.getPayload(), NewScheduledTaskEvent.class);

        Assertions.assertThat(output).isNotNull();
        Assertions.assertThat(output.getIdTask().length()).isEqualTo(36);
        Assertions.assertThat(output.getMessage()).isNotNull();
        Assertions.assertThat(output.getMessage()).contains(PackageStatusEnum.SENDING.name());
    }
}
