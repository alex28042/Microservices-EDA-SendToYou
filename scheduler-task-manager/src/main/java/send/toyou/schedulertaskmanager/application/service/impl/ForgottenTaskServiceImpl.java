package send.toyou.schedulertaskmanager.application.service.impl;

import send.toyou.schedulertaskmanager.application.service.ForgottenTaskService;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledForgottenTask;
import send.toyou.schedulertaskmanager.domain.dto.ScheduledTaskDto;
import send.toyou.schedulertaskmanager.domain.enums.DaysCronEnum;
import send.toyou.schedulertaskmanager.domain.enums.PeriodicityEnum;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;

public class ForgottenTaskServiceImpl implements ForgottenTaskService {
    @Override
    public boolean isForgottenTask(ScheduledForgottenTask scheduledForgottenTask) {
        if (scheduledForgottenTask.getTaskCreationDate() == null && scheduledForgottenTask.getLastExecutionDate() == null) {
            return false;
        }

        if (!scheduledForgottenTask.getDaysToBeExecuted().contains(DaysCronEnum.fromWeekDay(LocalDateTime.now().getDayOfWeek()))) {
            return false;
        }

        return getNextExecutionDate(scheduledForgottenTask).isBefore(LocalDateTime.now());
    }

    private LocalDateTime getNextExecutionDate(ScheduledForgottenTask scheduledForgottenTask) {
        return scheduledForgottenTask.getLastExecutionDate() == null ?
                generateNextExecutionDate(scheduledForgottenTask, scheduledForgottenTask.getTaskCreationDate()) :
                generateNextExecutionDate(scheduledForgottenTask, scheduledForgottenTask.getLastExecutionDate());
    }

    private LocalDateTime generateNextExecutionDate(ScheduledForgottenTask scheduledForgottenTask, LocalDateTime lastExecutionDate) {
        LocalTime localTime = LocalTime.of(
          Integer.parseInt(scheduledForgottenTask.getHour()),
          Integer.parseInt(scheduledForgottenTask.getMinute()),
          Integer.parseInt(scheduledForgottenTask.getSecond())
        );

        LocalDate localDate = null;

        if (scheduledForgottenTask.getPeriodicity().equals(PeriodicityEnum.DAILY)) {
            localDate = generateLocalDateForPeriodictiesDaily(scheduledForgottenTask, lastExecutionDate);
        }

        return LocalDateTime.of(localDate, localTime);
    }

    private LocalDate generateLocalDateForPeriodictiesDaily(ScheduledForgottenTask scheduledForgottenTask, LocalDateTime lastExecution) {
        if (scheduledForgottenTask.getDaysToBeExecuted().equals(new ArrayList<>(EnumSet.allOf(DaysCronEnum.class)))) {
            return lastExecution.plusDays(1).toLocalDate();
        }

        int nextDay = (scheduledForgottenTask.getDaysToBeExecuted().indexOf(DaysCronEnum.fromWeekDay(lastExecution.getDayOfWeek())) + 1)
                % scheduledForgottenTask.getDaysToBeExecuted().size();

        DayOfWeek nextDayDayOfWeek = DaysCronEnum.toDayOfWeek(scheduledForgottenTask.getDaysToBeExecuted().get(nextDay));

        int differenceInDaysBetweenTwoDates = Math.floorMod(nextDayDayOfWeek.getValue() - lastExecution.getDayOfWeek().getValue(), 7);

        return lastExecution.plusDays(differenceInDaysBetweenTwoDates).toLocalDate();
    }
}
