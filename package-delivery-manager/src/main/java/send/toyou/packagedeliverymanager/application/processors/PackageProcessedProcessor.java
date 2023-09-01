package send.toyou.packagedeliverymanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.PackageProcessedService;
import send.toyou.packagedeliverymanager.application.utils.MiscUtils;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.events.NewScheduledTaskEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

import java.util.UUID;

@Component
@Slf4j
public class PackageProcessedProcessor {
    @Autowired
    private PackageProcessedService packageProcessedService;

    public Flux<NewScheduledTaskEvent> process(final Flux<PackageProcessedEvent> inbound) {
        return inbound
                .doOnNext(pack -> log.info("Entry package processed: {}", pack))
                .map(Package::fromPackageProcessedEvent)
                .flatMap(pack -> {
                    pack.setStatus(PackageStatusEnum.SENDING.name());

                    return Mono.just(pack);
                })
                .flatMap(packageProcessedService::save)
                .map(PackageProcessedEvent::fromPackage)
                .map(packageProcessedService::getPackageDestinationAddress)
                .doOnNext(pack -> log.info("Package to be Scheduled: {}", pack))
                .flatMap(pack -> {
                    var scheduledTaskEvent = new NewScheduledTaskEvent();

                    scheduledTaskEvent.setIdTask(UUID.randomUUID().toString());
                    scheduledTaskEvent.setCron("0 30 14 * * ?");
                    scheduledTaskEvent.setMessage(MiscUtils.convertClassToMessage(pack));

                    return Mono.just(scheduledTaskEvent);
                })
                .doOnNext(task -> log.info("Task to be created: {}", task))
                .onErrorContinue(this::handleError);
    }
    
    private void handleError(Throwable throwable, Object object) {
        log.error("Error in PackageProcessedProcessor");
    }
}
