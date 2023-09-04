package send.toyou.eligibilitypackagemanager.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;
import send.toyou.eligibilitypackagemanager.domain.persistence.Package;

public interface EligibilityService {
    Mono<Package> processEligiblePackage(NewPackageEvent newPackageEvent);


}
