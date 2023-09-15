package send.toyou.mailmanager.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import send.toyou.mailmanager.application.processors.SendEmailProcessor;
import send.toyou.mailmanager.domain.events.EmailCompletedEvent;
import send.toyou.mailmanager.domain.events.NewEmailPackageEvent;

import java.util.function.Function;

@Configuration
public class StreamConfiguration {
    @Bean
    public Function<Flux<NewEmailPackageEvent>, Flux<EmailCompletedEvent>> emailProcessor(final SendEmailProcessor sendEmailProcessor) {
        return sendEmailProcessor::process;
    }
}
