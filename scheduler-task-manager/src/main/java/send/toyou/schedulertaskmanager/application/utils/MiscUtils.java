package send.toyou.schedulertaskmanager.application.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.util.StringUtils;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.enums.DaysCronEnum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class MiscUtils {
    public static boolean isValidatedNewTask(ScheduledTaskDto scheduledTaskDto) {

        if (!StringUtils.hasText(scheduledTaskDto.getDestination())) {
            log.info("ScheduledTask dont have destination: {}", scheduledTaskDto);
            return false;
        }

        if (!StringUtils.hasText(scheduledTaskDto.getCron()) || !isCronExpression(scheduledTaskDto.getCron())) {
            log.info("ScheduledTask dont have cron: {}", scheduledTaskDto);
            return false;
        }

        if (!StringUtils.hasText(scheduledTaskDto.getMessage())) {
            log.info("ScheduledTask dont have message to send: {}", scheduledTaskDto);
            return false;
        }

        return true;
    }

    public static boolean isCronExpression(String cronExpression) {
        try {
            new CronExpression(cronExpression);
            return true;
        } catch (ParseException e) {
            log.warn("cronExpression is invalid: {}", cronExpression);
            return false;
        }
    }

    public static Date getNextExecution(String cronExpr) throws ParseException{
        CronExpression cronExpression = new CronExpression(cronExpr);
        return cronExpression.getNextValidTimeAfter(new Date());
    }

    public static ArrayList<DaysCronEnum> convertArrayToArrayList(String[] cronExpressionDays) {
        ArrayList<DaysCronEnum> result = new ArrayList<>();

        for (String day : cronExpressionDays) {
            result.add(DaysCronEnum.valueOf(day));
        }

        return result;
    }
}
