package send.toyou.schedulertaskmanager.application.processors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.test.context.ActiveProfiles;
import send.toyou.schedulertaskmanager.application.service.TaskScheduledService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.events.NewScheduledTaskEvent;
import send.toyou.schedulertaskmanager.domain.events.UpdateJobStoreEvent;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTask;
import send.toyou.schedulertaskmanager.domain.repositories.ScheduledTaskRepository;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@Slf4j
@ActiveProfiles("test")
public class TaskScheduleProcessorTest {
    static MockedStatic<ScheduledTask> utilities;

    static MockedStatic<UpdateJobStoreEvent> utilitiesUpdate;

    @Mock
    TaskScheduledService taskScheduledService;

    @Mock
    ScheduledTaskRepository scheduledTaskRepository;

    @Mock
    ScheduledTask scheduledTask;

    @InjectMocks
    NewTaskToScheduleProcessor processor;

    @BeforeEach
    void init() {
        utilities = Mockito.mockStatic(ScheduledTask.class);
        utilitiesUpdate = Mockito.mockStatic(UpdateJobStoreEvent.class);
    }

    @AfterEach
    void after() {
        utilities.close();
        utilitiesUpdate.close();
    }

    @Test
    void testSetExistingId() {
        var updateJobStoreEvent = new UpdateJobStoreEvent();
        updateJobStoreEvent.setIdTask("111");
        updateJobStoreEvent.setMessage("message");
        updateJobStoreEvent.setCron("Cron");
        updateJobStoreEvent.setDescription("description");
        updateJobStoreEvent.setDestination("destination");
        updateJobStoreEvent.setLastExecutionDate("lastat");
        updateJobStoreEvent.setTaskCreationDate("creation");

        var scheduleTask = new ScheduleTask();
        scheduleTask.setIdTask("111");
        scheduleTask.setMessage("message");
        scheduleTask.setCron("Cron");
        scheduleTask.setDescription("description");
        scheduleTask.setDestination("destination");
        scheduleTask.setLastExecutionDate("lastat");
        scheduleTask.setTaskCreationDate("creation");

        var scheduledTaskDto = new ScheduledTaskDto();
        scheduledTaskDto.setIdTask("111");
        scheduledTaskDto.setMessage("message");
        scheduledTaskDto.setCron("Cron");
        scheduledTaskDto.setDescription("description");
        scheduledTaskDto.setDestination("destination");

        var newScheduledTaskEvent = new NewScheduledTaskEvent();
        newScheduledTaskEvent.setIdTask("111");
        newScheduledTaskEvent.setMessage("message");
        newScheduledTaskEvent.setCron("Cron");
        newScheduledTaskEvent.setDescription("description");
        newScheduledTaskEvent.setDestination("destination");

        utilities.when(() -> ScheduleTask.fromNewScheduledTaskEvent(newScheduledTaskEvent))
                .thenReturn(scheduleTask);
    }
}
