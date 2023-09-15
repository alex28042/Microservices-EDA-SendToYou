package send.toyou.schedulertaskmanager.domain.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import send.toyou.schedulertaskmanager.application.service.TriggerService;
import send.toyou.schedulertaskmanager.application.service.UpdateJobStoreService;
import send.toyou.schedulertaskmanager.application.service.impl.TaskScheduledServiceImpl;
import send.toyou.schedulertaskmanager.application.service.impl.UpdateJobStoreServiceImpl;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTaskAbstract;

@Slf4j
public class ScheduledJobTrigger extends ScheduleTaskAbstract implements Job {
    @Autowired
    private TriggerService triggerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var scheduledTaskDto = UpdateJobStoreServiceImpl.buildScheduledTaskDtoFromJobData(jobExecutionContext.getJobDetail().getJobDataMap());

        log.info("execute trigger task: {}", scheduledTaskDto);

        this.triggerService.processJob(scheduledTaskDto);

        log.info("executed process job");
    }
}
