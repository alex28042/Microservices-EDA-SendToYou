package send.toyou.mailmanager.application.processors;

import io.r2dbc.spi.ConnectionFactory;
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
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

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
    }
}
