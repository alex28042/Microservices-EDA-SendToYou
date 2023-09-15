package send.toyou.packagedeliverymanager.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import send.toyou.packagedeliverymanager.application.processors.NewPackageProcessor;
import send.toyou.packagedeliverymanager.application.processors.PackageProcessedProcessor;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.NewScheduledTaskEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.application.processors.PackageSentProcessor;
import send.toyou.packagedeliverymanager.domain.events.*;

import java.util.function.Function;

@Configuration
public class StreamConfiguration {
    @Bean
    public Function<Flux<NewPackageEvent>, Flux<NewScheduledTaskEvent>> newPackageBinding(final NewPackageProcessor newPackageProcessor) {
        return newPackageProcessor::process;
    }

    @Bean
    public Function<Flux<PackageProcessedEvent>, Flux<NewScheduledTaskEvent>> packageSendingBinding(final PackageProcessedProcessor packageProcessedProcessor) {
        return packageProcessedProcessor::process;
    }

    @Bean
    public Function<Flux<PackageSentEvent>, Flux<NewDeleteTaskEvent>> packageDeliveredBinding(final PackageSentProcessor packageSentProcessor) {
        return packageSentProcessor::process;
    }
}
