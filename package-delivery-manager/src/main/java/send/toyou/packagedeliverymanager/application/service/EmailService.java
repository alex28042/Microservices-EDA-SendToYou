package send.toyou.packagedeliverymanager.application.service;

import reactor.core.publisher.Mono;

public interface EmailService {
    Mono<Void> sendEmail(Object object);
}
