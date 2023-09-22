package send.toyou.packagemicroapi.application.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.persistence.Package;

public interface PackageService {
    Mono<Package> save(Package packageToCreate);
    Mono<Package> getPackageById(String id);
    Flux<Package> getAllPackages();
    Mono<Void> deletePackage(Package packageToCreate);
}
