package send.toyou.packagedeliverymanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.EmailService;
import send.toyou.packagedeliverymanager.application.service.PackageSentService;
import send.toyou.packagedeliverymanager.application.utils.MiscUtils;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.events.NewDeleteTaskEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageSentEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

import java.util.UUID;
@Component
@Slf4j
public class PackageSentProcessor {
    @Autowired
    private PackageSentService packageSentService;

    @Autowired
    private EmailService emailService;

    public Flux<NewDeleteTaskEvent> process(final Flux<PackageSentEvent> inbound) {
        return inbound
                .flatMap(event -> {
                    event.setStatus(PackageStatusEnum.DELIVERED.name());

                    return Mono.just(event);
                })
                .doOnNext(pack -> log.info("Entry package: {}", pack))
                .map(Package::fromPackageSentEvent)
                .flatMap(packageSentService::saveToDB)
                .map(PackageSentEvent::fromPackage)
                .flatMap(packageSentService::setDestinationPackage)
                .doOnNext(pack -> log.info("Destination Loaded: {}", pack))
                .doOnNext(emailService::sendEmail)
                .doOnNext(packageSentEvent -> log.info("Email event sent: {}", packageSentEvent))
                .flatMap(pack -> {
                    var deleteTaskEvent = new NewDeleteTaskEvent();

                    deleteTaskEvent.setIdTask(UUID.randomUUID().toString());
                    deleteTaskEvent.setCron("0 30 14 * * ?");
                    deleteTaskEvent.setMessage(MiscUtils.convertClassToMessage(pack));

                    return Mono.just(deleteTaskEvent);
                })
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error from PackageSentProcessor: {}", object, throwable);
    }
}
