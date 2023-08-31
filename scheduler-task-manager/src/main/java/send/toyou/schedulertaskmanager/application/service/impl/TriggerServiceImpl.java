package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import send.toyou.schedulertaskmanager.application.service.TriggerService;
import send.toyou.schedulertaskmanager.application.utils.MiscUtils;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.JobTriggerEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class TriggerServiceImpl implements TriggerService {
    private static final String TOPIC_DESTINATION = "sendtoyou.scheduler-job-triggers";

    @Autowired
    private StreamBridge streamBridge;
    @Override
    public void processJob(ScheduledTaskDto scheduledTaskDto) {
        JobTriggerEvent jobTriggerEvent = new JobTriggerEvent(
            scheduledTaskDto.getIdTask(),
            scheduledTaskDto.getMessage(),
            scheduledTaskDto.getCron(),
            scheduledTaskDto.getDestination(),
            scheduledTaskDto.getDescription(),
            scheduledTaskDto.getTaskCreationDate(),
            scheduledTaskDto.getLastExecutionDate()
        );

        var duplicationKey = generateTriggerKey(scheduledTaskDto.getIdTask(), scheduledTaskDto.getCron());

        MessageBuilder<JobTriggerEvent> messageBuilder =  MessageBuilder.withPayload(jobTriggerEvent);
        messageBuilder.setHeader(KafkaHeaders.KEY, duplicationKey);

        var output = messageBuilder.build();

        try {
            this.streamBridge.send(TOPIC_DESTINATION, output);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    private String generateTriggerKey(String id, String cron) {
        Date nextJobExec;

        try {
            nextJobExec = MiscUtils.getNextExecution(cron);
        } catch (ParseException e) {
            log.error("Error parsing get next Execution");
            throw new RuntimeException(e);
        }

        String nextExec = nextJobExec != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(nextJobExec) :
                "FINAL_EXEC";

        return id + "-" + nextExec;
    }
}
