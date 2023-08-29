package send.toyou.schedulertaskmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import send.toyou.schedulertaskmanager.application.service.TaskScheduledService;
import send.toyou.schedulertaskmanager.application.service.UpdateJobStoreService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.UpdateJobStoreEvent;

@Component
@Slf4j
public class updateJobProcessor {
    @Autowired
    private UpdateJobStoreService taskScheduledService;

    public Flux<ScheduledTaskDto> process(Flux<UpdateJobStoreEvent> inbound) {
        return inbound
                .doOnNext(event -> log.info("Event Update JobStore: {}", inbound))
                .map(ScheduledTaskDto::fromUpdateJobStoreEvent)
                .map(taskScheduledService::updateJobStore)
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error UpdateJobProcessor");
    }
}
