package send.toyou.schedulertaskmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.schedulertaskmanager.application.service.LaunchService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;

@Component
@Slf4j
public class LauncherTaskProcessor {
    @Autowired
    private LaunchService launchService;

    public Flux<ScheduledTaskDto> process(final Flux<JobTriggerEvent> input) {
        return input
                .map(ScheduledTaskDto::fromJobTriggerEvent)
                .flatMap(launchService::launchScheduledTask)
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error LaucherTaskProcessor: {}", object, throwable);
    }

}
