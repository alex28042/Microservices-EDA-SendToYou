package send.toyou.eligibilitypackagemanager.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import send.toyou.eligibilitypackagemanager.application.processors.EligibilityProcessor;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;

import java.util.function.Function;

@Configuration
public class StreamConfiguration {
    @Bean
    public Function<Flux<NewPackageEvent>, Flux<NewPackageEvent>> eligibilityBinding(final EligibilityProcessor eligibilityProcessor) {
        return eligibilityProcessor::process;
    }
}
