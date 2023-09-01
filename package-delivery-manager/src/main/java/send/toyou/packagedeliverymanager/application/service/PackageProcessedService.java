package send.toyou.packagedeliverymanager.application.service;

import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

public interface PackageProcessedService {
    Mono<Package> save(Package packageToUpdate);
    Mono<PackageProcessedEvent> getPackageDestinationAddress(PackageProcessedEvent pack);
}
