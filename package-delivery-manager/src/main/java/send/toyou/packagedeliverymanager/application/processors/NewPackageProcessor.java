package send.toyou.packagedeliverymanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.packagedeliverymanager.application.service.NewPackageService;
import send.toyou.packagedeliverymanager.application.service.impl.NewPackageServiceImpl;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;

@Component
@Slf4j
public class NewPackageProcessor {
    @Autowired
    private NewPackageServiceImpl newPackageService;

    public Flux<NewPackageEvent> process(Flux<NewPackageEvent> inbound) {
        return inbound
                .doOnNext(newPackageEvent -> log.info("Entry newPackageEvent: {}", newPackageEvent))
                .flatMap(newPackageService::getPackageDestinationAddress)
                .doOnNext(newPackageEvent -> log.info("Destination Loaded: {}", newPackageEvent))
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error with exception: {} and object: {}", throwable, object);
    }
}
