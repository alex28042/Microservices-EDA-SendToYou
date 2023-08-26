package send.toyou.packagemicroapi.application;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.persistence.Package;

public interface PackageService {
    Mono<Package> save(Package packageToCreate);
    Mono<Package> getPackageById(Long id);
    Flux<Package> getAllPackages();
    Mono<Package> deletePackage(Package packageToCreate);
}
