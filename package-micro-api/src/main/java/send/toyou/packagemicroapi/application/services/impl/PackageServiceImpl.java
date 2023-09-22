package send.toyou.packagemicroapi.application.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.services.PackageService;
import send.toyou.packagemicroapi.application.utils.MiscUtils;
import send.toyou.packagemicroapi.domain.constants.MonitoringConstants;
import send.toyou.packagemicroapi.domain.constants.PackageConstants;
import send.toyou.packagemicroapi.domain.enums.PackageStatusEnum;
import send.toyou.packagemicroapi.domain.events.NewPackageEvent;
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
        return MiscUtils.parsePackageWithUuid(packageToCreate)
                .flatMap(packageRepository::save)
                .doOnNext(packageCreated -> log.info("Package in DB with id: {}", packageCreated.getId()))
                .flatMap(packageCreated -> {
                    if (packageCreated != null) {
                        packageCreated.setStatus(PackageStatusEnum.PROCESSING.name());
                        return Mono.just(packageCreated);
                    }

                    log.info("Package doesn't created: {}", packageToCreate);
                    return Mono.empty();
                })
                .filter(Objects::nonNull)
                .doOnNext(packageToSend -> log.info("Package to send: {}", packageToSend))
                .doOnNext(packageToSend -> this.streamBridge.send(PackageConstants.PACKAGE_BINDING, NewPackageEvent.fromPackage(packageToSend)))
                .onErrorContinue(this::handleError);
    }

    @Override
    public Mono<Package> getPackageById(String id) {
        return this.packageRepository.findByIdPackage(id)
                .doOnNext(pack -> log.info("Find Package with id: {}", pack))
                .flatMap(pack -> {
                    if (pack != null) {
                        return Mono.just(pack);
                    }

                    log.info("Package not found: {}", id);
                    return Mono.empty();
                })
                .onErrorContinue(this::handleError);
    }

    @Override
    public Flux<Package> getAllPackages() {
        return this.packageRepository.findAll()
                .onErrorContinue(this::handleError);
    }

    @Override
    public Mono<Void> deletePackage(Package packageToDelete) {
        return this.packageRepository.delete(packageToDelete)
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error with exception: {} and object: {}", throwable, object);
        this.monitoringService.sendValidationErrors(object, MonitoringConstants.MONITORING_FROM_ERROR, throwable.getMessage());
    }
}
