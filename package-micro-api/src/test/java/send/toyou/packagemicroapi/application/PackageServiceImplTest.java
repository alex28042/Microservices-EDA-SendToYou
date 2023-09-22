package send.toyou.packagemicroapi.application;

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
import send.toyou.packagemicroapi.application.services.impl.PackageServiceImpl;
import send.toyou.packagemicroapi.domain.persistence.Package;
import send.toyou.packagemicroapi.domain.repositories.PackageRepository;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class PackageServiceImplTest {
    @Autowired
    private PackageServiceImpl packageService;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private ConnectionFactory connectionFactory;

    private void executeScriptBlocking(final Resource resource) {
        Mono.from(connectionFactory.create())
                .flatMap(connection -> ScriptUtils.executeSqlScript(connection, resource))
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
    void packageServiceSave(@Value("classpath:/insert-data.sql") Resource insertData) {
        executeScriptBlocking(insertData);

        var pack = new Package();
        pack.setName("test");

        Package result = this.packageService.save(pack).block();

        System.out.println(result);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), pack.getName());
    }

    @Test
    void packageServiceFindById(@Value("classpath:/insert-data.sql") Resource insertData) {
        executeScriptBlocking(insertData);

        Package result = this.packageService.getPackageById("test").block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), "test");
    }
}
