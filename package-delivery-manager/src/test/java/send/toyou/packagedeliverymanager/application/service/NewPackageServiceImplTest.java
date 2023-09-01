package send.toyou.packagedeliverymanager.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class NewPackageServiceImplTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private NewPackageService newPackageService;

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

    @Test
    void getPackageDestinationAddress(@Value("classpath:/insert-data.sql") Resource resource) {
        executeScriptBlocking(resource);
        var packageEvent = new NewPackageEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("1");

        NewPackageEvent event = newPackageService.getPackageDestinationAddress(packageEvent).block();

        Assertions.assertNotNull(event);
        Assertions.assertEquals(event.getAddressDestination().getNumber(), "22");
    }

    @Test
    void getPackageDestinationAddressFails(@Value("classpath:/insert-data.sql") Resource resource) {
        executeScriptBlocking(resource);
        var packageEvent = new NewPackageEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("4");

        NewPackageEvent event = newPackageService.getPackageDestinationAddress(packageEvent).block();

        Assertions.assertNull(event);
    }
}
