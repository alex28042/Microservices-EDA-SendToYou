package send.toyou.packagemicroapi.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import send.toyou.packagemicroapi.application.useCases.impl.PackageServiceImpl;
import send.toyou.packagemicroapi.domain.repositories.PackageRepository;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestChannelBinderConfiguration.class)
public class PackageServiceImplTest {
    @Autowired
    private PackageServiceImpl packageService;

    @Autowired
    private PackageRepository packageRepository;

    @Test
    void packageServiceSave() {

    }
}
