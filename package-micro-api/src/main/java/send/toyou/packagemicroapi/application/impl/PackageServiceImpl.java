package send.toyou.packagemicroapi.application.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.PackageService;
import send.toyou.packagemicroapi.domain.constants.MonitoringConstants;
import send.toyou.packagemicroapi.domain.constants.PackageConstants;
import send.toyou.packagemicroapi.domain.persistence.Package;
import send.toyou.packagemicroapi.domain.repositories.PackageRepository;

import java.util.Objects;

@Service
@Slf4j
public class PackageServiceImpl implements PackageService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Override
    public Mono<Package> save(Package packageToCreate) {
        return this.packageRepository.findById(packageToCreate.getId())
                .doOnNext(packageFinded -> log.info("Package in DB with id: {}", packageFinded.getId()))
                .flatMap(packageFinded -> {
                    if (packageFinded != null) {
                        this.packageRepository.save(packageToCreate);
                        return Mono.just(packageToCreate);
                    }

                    return Mono.empty();
                })
                .filter(Objects::nonNull)
                .doOnNext(packageToSend -> log.info("Package to send: {}", packageToSend))
                .doOnNext(packageToSend -> this.streamBridge.send(PackageConstants.PACKAGE_BINDING, packageToSend))
                .onErrorContinue(this::handleError);
    }

    @Override
    public Mono<Package> getPackageById(Long id) {
        return null;
    }

    @Override
    public Flux<Package> getAllPackages() {
        return null;
    }

    @Override
    public Mono<Package> deletePackage(Package packageToCreate) {
        return null;
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error with exception: {} and object: {}", throwable, object);
        this.monitoringService.sendValidationErrors(object, MonitoringConstants.MONITORING_FROM_ERROR, throwable.getMessage());
    }
}
