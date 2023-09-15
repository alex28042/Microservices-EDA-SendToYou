package send.toyou.eligibilitypackagemanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.eligibilitypackagemanager.application.service.EligibilityService;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;

@Component
@Slf4j
public class EligibilityProcessor {
    @Autowired
    private EligibilityService eligibilityService;

    public Flux<NewPackageEvent> process(Flux<NewPackageEvent> inbound) {
        return inbound
                .doOnNext(newPackageEvent -> log.info("New Entry package event: {}", newPackageEvent))
                .flatMap(eligibilityService::processEligiblePackage)
                .doOnNext(pack -> log.info("Eligibility package completed with package result: {}", pack))
                .map(NewPackageEvent::fromPackage)
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error in Eligibility processor: {}", object, throwable);
    }
}
