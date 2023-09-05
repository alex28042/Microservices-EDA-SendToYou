package send.toyou.eligibilitypackagemanager.application.processors;


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
import send.toyou.eligibilitypackagemanager.domain.enums.PackageStatusEnum;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class EligibilityProcessorTest {
    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private InputDestination inputDestination;

    private final int TIME_OUT = 5000;

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
    void processTestNo_Eligible(@Value("classpath:/insert-data.sql") Resource insert) {
        executeScriptBlocking(insert);

        var packageEvent = new NewPackageEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("2");
        packageEvent.setStatus(PackageStatusEnum.PROCESSING.name());

        MessageBuilder<NewPackageEvent> packageEventMessageBuilder = MessageBuilder.withPayload(packageEvent);

        this.inputDestination.send(packageEventMessageBuilder.build(), "sendtoyou.new-package-event");

        Message<byte[]> errorResponse = outputDestination.receive(TIME_OUT, "sendtoyou.new-package-eligible-completed");

        final var output = objectMapper.readValue(errorResponse.getPayload(), NewPackageEvent.class);

        Assertions.assertThat(output).isNotNull();
    }

    @SneakyThrows
    @Test
    void processTestProcessing(@Value("classpath:/insert-data.sql") Resource insert) {
        executeScriptBlocking(insert);

        var packageEvent = new NewPackageEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("2");
        packageEvent.setPackageSize(4);
        packageEvent.setStatus(PackageStatusEnum.PROCESSING.name());

        MessageBuilder<NewPackageEvent> packageEventMessageBuilder = MessageBuilder.withPayload(packageEvent);

        this.inputDestination.send(packageEventMessageBuilder.build(), "sendtoyou.new-package-event");

        Message<byte[]> errorResponse = outputDestination.receive(TIME_OUT, "sendtoyou.new-package-eligible-completed");

        final var output = objectMapper.readValue(errorResponse.getPayload(), NewPackageEvent.class);

        Assertions.assertThat(output).isNotNull();
    }
}

