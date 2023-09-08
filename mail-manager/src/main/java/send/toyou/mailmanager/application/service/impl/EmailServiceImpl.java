package send.toyou.mailmanager.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.mailmanager.application.service.EmailService;
import send.toyou.mailmanager.domain.events.EmailCompletedEvent;
import send.toyou.mailmanager.domain.events.NewEmailPackageEvent;

import java.util.Arrays;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public Mono<EmailCompletedEvent> sendEmail(NewEmailPackageEvent event) {
        var emailContent = new SimpleMailMessage();

        emailContent.setFrom("email sender");
        emailContent.setTo(event.getEmailReceipter());
        emailContent.setSubject("Package Status");
        emailContent.setText(event.getPackageStatusEnum().name());

        this.mailSender.send(emailContent);

        var emailCompleted = generateEmailCompleted(emailContent);

        return Mono.just(emailCompleted);
    }

    private EmailCompletedEvent generateEmailCompleted(SimpleMailMessage simpleMailMessage) {
        var event = new EmailCompletedEvent();

        event.setEmailReceipter(Arrays.toString(simpleMailMessage.getTo()));
        event.setSent(true);
        event.setMessage(simpleMailMessage.getText());

        return event;
    }
}
