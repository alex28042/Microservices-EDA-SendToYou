package send.toyou.packagedeliverymanager.application.service;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.repositories.PackageRepository;
import send.toyou.packagedeliverymanager.domain.repositories.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class PackageProcessedServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private PackageProcessedService packageProcessedService;

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
        var packageEvent = new PackageProcessedEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("1");

        PackageProcessedEvent event = packageProcessedService.getPackageDestinationAddress(packageEvent).block();

        Assertions.assertNotNull(event);
        Assertions.assertEquals(event.getAddressDestination().getNumber(), "22");
    }

    @Test
    void getPackageDestinationAddressFails(@Value("classpath:/insert-data.sql") Resource resource) {
        executeScriptBlocking(resource);
        var packageEvent = new PackageProcessedEvent();
        packageEvent.setId("test");
        packageEvent.setSenderUserId("1");
        packageEvent.setReceipterUserId("4");

        PackageProcessedEvent event = packageProcessedService.getPackageDestinationAddress(packageEvent).block();

        Assertions.assertNull(event);
    }

    @Test
    void savePackage() {
        var pack = new Package();
        pack.setId("111111");
        pack.setName("fafff");

        this.packageProcessedService.save(pack).block();

        Package packFound = this.packageRepository.findById(pack.getId()).block();

        Assertions.assertNotNull(packFound);
    }
}
