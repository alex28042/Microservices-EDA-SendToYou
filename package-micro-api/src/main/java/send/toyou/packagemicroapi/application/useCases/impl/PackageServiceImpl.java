package send.toyou.packagemicroapi.application.useCases.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.useCases.PackageService;
import send.toyou.packagemicroapi.application.utils.MiscUtils;
import send.toyou.packagemicroapi.domain.constants.MonitoringConstants;
import send.toyou.packagemicroapi.domain.constants.PackageConstants;
import send.toyou.packagemicroapi.domain.persistence.Package;
import send.toyou.packagemicroapi.domain.repositories.PackageRepository;

import java.util.Objects;
import java.util.UUID;

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
        return MiscUtils.parsePackageWithUuid(packageToCreate)
                .flatMap(packageRepository::save)
                .doOnNext(packageCreated -> log.info("Package in DB with id: {}", packageCreated.getId()))
                .flatMap(packageCreated -> {
                    if (packageCreated != null) {
                        return Mono.just(packageCreated);
                    }

                    log.info("Package doesn't created: {}", packageToCreate);
                    return Mono.empty();
                })
                .filter(Objects::nonNull)
                .doOnNext(packageToSend -> log.info("Package to send: {}", packageToSend))
                .doOnNext(packageToSend -> this.streamBridge.send(PackageConstants.PACKAGE_BINDING, packageToSend))
                .onErrorContinue(this::handleError);
    }

    @Override
    public Mono<Package> getPackageById(String id) {
        return this.packageRepository.findById(id)
                .doOnNext(pack -> log.info("Find Package with id: {}", pack))
                .flatMap(pack -> {
                    if (pack != null) {
                        return Mono.just(pack);
                    }

                    log.info("Package not found: {}", id);
                    return Mono.empty();
                });
    }

    @Override
    public Flux<Package> getAllPackages() {
        return this.packageRepository.findAll();
    }

    @Override
    public Mono<Void> deletePackage(Package packageToDelete) {
        return this.packageRepository.delete(packageToDelete)
;    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error with exception: {} and object: {}", throwable, object);
        this.monitoringService.sendValidationErrors(object, MonitoringConstants.MONITORING_FROM_ERROR, throwable.getMessage());
    }
}
