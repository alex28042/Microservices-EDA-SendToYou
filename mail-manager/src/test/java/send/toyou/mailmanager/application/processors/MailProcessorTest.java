package send.toyou.mailmanager.application.processors;

import io.r2dbc.spi.ConnectionFactory;
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
import send.toyou.mailmanager.domain.enums.PackageStatusEnum;
import send.toyou.mailmanager.domain.events.NewEmailPackageEvent;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class MailProcessorTest {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private InputDestination inputDestination;

    private final int TIME_OUT = 5000;

    @BeforeEach
    void init(@Value("classpath:/schema.sql") Resource resource) {
        this.executeScriptBlocking(resource);
    }

    @AfterEach
    void after(@Value("classpath:/clear.sql") Resource resource) {
        this.executeScriptBlocking(resource);
    }

    private void executeScriptBlocking(final Resource sqlScript) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, sqlScript))
                .subscribe();
    }

    @Test
    void processorTest(@Value("classpath:/insert-data.sql") Resource resource) {
        this.executeScriptBlocking(resource);

        var newEmailPackageEvent = new NewEmailPackageEvent();
        newEmailPackageEvent.setEmailReceipter("alonso.garcia.dev@gmail.com");
        newEmailPackageEvent.setPackageStatusEnum(PackageStatusEnum.PROCESSING);

        Message<NewEmailPackageEvent> message = MessageBuilder.withPayload(newEmailPackageEvent).build();
        this.inputDestination.send(message, "sendtoyou.new-email-event");

        var result = this.outputDestination.receive(TIME_OUT, "sendtoyou.email-completed");
        var emailCompleted = result.getPayload();

        Assertions.assertThat(emailCompleted).isNotNull();
    }
}
