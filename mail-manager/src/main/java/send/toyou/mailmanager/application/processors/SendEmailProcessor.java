package send.toyou.mailmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.mailmanager.application.service.EmailService;
import send.toyou.mailmanager.domain.events.EmailCompletedEvent;
import send.toyou.mailmanager.domain.events.NewEmailPackageEvent;

@Component
@Slf4j
public class SendEmailProcessor {
    @Autowired
    private EmailService emailService;

    public Flux<EmailCompletedEvent> process(final Flux<NewEmailPackageEvent> inbound) {
        return inbound
                .doOnNext(email -> log.info("Entry email contains: {}", email))
                .flatMap(emailService::sendEmail)
                .doOnNext(emailCompletedEvent -> log.info("Email Completed: {}", emailCompletedEvent))
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error int SendEmailProcessor with object {}", object, throwable);
    }
}
