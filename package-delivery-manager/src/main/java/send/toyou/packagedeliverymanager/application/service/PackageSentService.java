package send.toyou.packagedeliverymanager.application.service;

import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.events.PackageSentEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

public interface PackageSentService {
    Mono<Package> saveToDB(Package pack);

    Mono<PackageSentEvent> setDestinationPackage(PackageSentEvent packageSentEvent);
}
