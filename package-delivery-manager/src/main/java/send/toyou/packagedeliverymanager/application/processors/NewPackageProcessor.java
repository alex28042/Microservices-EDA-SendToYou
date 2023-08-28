package send.toyou.packagedeliverymanager.application.processors;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;

@Component
public class NewPackageProcessor {

    public Flux<NewPackageEvent> process(Flux<NewPackageEvent> inbound) {
        return inbound;
    }
}
