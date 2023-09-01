package send.toyou.packagedeliverymanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.EmailService;
import send.toyou.packagedeliverymanager.application.service.NewPackageService;
import send.toyou.packagedeliverymanager.application.service.impl.NewPackageServiceImpl;
import send.toyou.packagedeliverymanager.application.utils.MiscUtils;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.NewScheduledTaskEvent;

import java.util.UUID;

@Component
@Slf4j
public class NewPackageProcessor {
    @Autowired
    private NewPackageServiceImpl newPackageService;
    @Autowired
    private EmailService emailService;

    public Flux<NewScheduledTaskEvent> process(Flux<NewPackageEvent> inbound) {
        return inbound
                .doOnNext(newPackageEvent -> log.info("Entry newPackageEvent: {}", newPackageEvent))
                .flatMap(newPackageService::getPackageDestinationAddress)
                .doOnNext(newPackageEvent -> log.info("Destination Loaded: {}", newPackageEvent))
                .doOnNext(emailService::sendEmail)
                .flatMap(newPackageEvent -> {
                    final var newScheduledTaskEvent = new NewScheduledTaskEvent();
                    newScheduledTaskEvent.setIdTask(UUID.randomUUID().toString());
                    newScheduledTaskEvent.setCron("0 30 14 * * ?");
                    newScheduledTaskEvent.setMessage(MiscUtils.convertClassToMessage(newPackageEvent));

                    return Flux.just(newScheduledTaskEvent);
                })
                .doOnNext(newScheduledTaskEvent -> log.info("New Task to create: {}", newScheduledTaskEvent))
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error with exception: {} and object: {}", throwable, object);
    }
}
