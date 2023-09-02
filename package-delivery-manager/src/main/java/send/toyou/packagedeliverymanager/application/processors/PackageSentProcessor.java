package send.toyou.packagedeliverymanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.utils.MiscUtils;
import send.toyou.packagedeliverymanager.domain.events.NewDeleteTaskEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageSentEvent;

import java.util.UUID;
@Component
@Slf4j
public class PackageSentProcessor {
    public Flux<NewDeleteTaskEvent> process(final Flux<PackageSentEvent> inbound) {
        return inbound
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
