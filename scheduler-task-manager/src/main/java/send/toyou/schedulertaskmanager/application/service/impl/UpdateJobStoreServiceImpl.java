package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.core.QuartzScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Service;
import send.toyou.schedulertaskmanager.application.service.UpdateJobStoreService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.job.ScheduledJobTrigger;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UpdateJobStoreServiceImpl implements UpdateJobStoreService {
    @Autowired
    private Scheduler quartzScheduler;

    @Override
    public ScheduledTaskDto updateJobStore(ScheduledTaskDto scheduledTaskDto) {
        return null;
    }

    @Override
    public ScheduledTaskDto deletingJobstore(ScheduledTaskDto scheduledTaskDto) {
        return null;
    }

    private void scheduleTask(ScheduledTaskDto scheduledTaskDto) {
        try {
            var jobkey = new JobKey("job-" + scheduledTaskDto.getIdTask());
            var triggerKey = new TriggerKey("job-" + scheduledTaskDto.getIdTask());

            if (this.quartzScheduler.checkExists(jobkey)) {
                log.warn("scheduledtask job key already exists: {}",jobkey);
                log.info("Rescheduling task");

                this.quartzScheduler.unscheduleJob(triggerKey);
                this.quartzScheduler.deleteJob(jobkey);
            }

            JobDetail jobDetail = buildJobDetail(scheduledTaskDto);
            CronTrigger cronTrigger = buildCronTrigger(scheduledTaskDto);

            this.quartzScheduler.scheduleJob(jobDetail, cronTrigger);
            log.info("Scheduled Task: {}", scheduledTaskDto);
        } catch (Exception e) {
            log.error("Error scheduling task: {}", scheduledTaskDto);
        }
    }

    private JobDetail buildJobDetail(ScheduledTaskDto scheduledTaskDto) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(ScheduledJobTrigger.class);
        jobDetailFactoryBean.setName("job-" + scheduledTaskDto.getIdTask());
        jobDetailFactoryBean.setJobDataAsMap(buildJobDataMapFromDto(scheduledTaskDto));
        jobDetailFactoryBean.afterPropertiesSet();

        return jobDetailFactoryBean.getObject();
    }

    private CronTrigger buildCronTrigger(ScheduledTaskDto scheduledTaskDto) throws ParseException {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setName("trigger-" + scheduledTaskDto.getIdTask());
        cronTriggerFactoryBean.setCronExpression(scheduledTaskDto.getCron());
        cronTriggerFactoryBean.afterPropertiesSet();

        return cronTriggerFactoryBean.getObject();
    }

    public static Map<String, String> buildJobDataMapFromDto(ScheduledTaskDto scheduledTaskDto) {
        Map<String, String> map = new HashMap<>();

        map.put("id", scheduledTaskDto.getIdTask());
        map.put("mess", scheduledTaskDto.getMessage());
        map.put("cron", scheduledTaskDto.getCron());
        map.put("dest", scheduledTaskDto.getDestination());
        map.put("desc", scheduledTaskDto.getDescription());
        map.put("taskCr", scheduledTaskDto.getTaskCreationDate());
        map.put("lastEx", scheduledTaskDto.getLastExecutionDate());

        return map;
    }

    public static ScheduledTaskDto buildScheduledTaskDtoFromJobData(JobDataMap jobDataMap) {
        return new ScheduledTaskDto(
            jobDataMap.getString("id"),
            jobDataMap.getString("mess"),
            jobDataMap.getString("cron"),
            jobDataMap.getString("dest"),
            jobDataMap.getString("desc"),
            jobDataMap.getString("taskCr"),
            jobDataMap.getString("lastEx")
        );
    }
}
