package send.toyou.packagedeliverymanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.EmailService;
import send.toyou.packagedeliverymanager.domain.constants.EmailConstants;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.events.NewEmailPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageEventFields;
import send.toyou.packagedeliverymanager.domain.repositories.UserRepository;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreamBridge streamBridge;

    @Override
    public void sendEmail(Object object) {
        if (!(object instanceof PackageEventFields)) {
            log.error("{} cant be sent by email", object);
            return;
        }

        var packageToSendEmail = (PackageEventFields) object;

        this.userRepository.findById(packageToSendEmail.getReceipterUserId())
                .doOnNext(user -> log.info("User to send email: {}", user))
                .flatMap(user -> {
                    if (user.getEmail().isBlank() || user.getEmail() == null) {
                        log.info("No Email for userId: {}", user.getId());
                        return Mono.empty();
                    }

                    var emailEvent = new NewEmailPackageEvent();

                    emailEvent.setEmailReceipter(user.getEmail());
                    emailEvent.setPackageStatusEnum(PackageStatusEnum.valueOf(packageToSendEmail.getStatus()));

                    return Mono.just(emailEvent);
                })
                .doOnNext(emailEvent -> log.info("Email event loaded: {}", emailEvent))
                .doOnNext(emailEvent -> this.streamBridge.send(EmailConstants.EMAIL_TOPIC, emailEvent));

    }
}
