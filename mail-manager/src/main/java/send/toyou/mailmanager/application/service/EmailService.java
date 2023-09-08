package send.toyou.mailmanager.application.service;

import reactor.core.publisher.Mono;
import send.toyou.mailmanager.domain.events.EmailCompletedEvent;
import send.toyou.mailmanager.domain.events.NewEmailPackageEvent;

public interface EmailService {
    Mono<EmailCompletedEvent> sendEmail(NewEmailPackageEvent event);
}
