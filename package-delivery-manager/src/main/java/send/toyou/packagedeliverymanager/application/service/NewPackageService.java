package send.toyou.packagedeliverymanager.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;

public interface NewPackageService {
    Mono<NewPackageEvent> getPackageDestinationAddress(NewPackageEvent pack);
}
