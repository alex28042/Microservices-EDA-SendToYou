package send.toyou.schedulertaskmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import send.toyou.schedulertaskmanager.application.utils.MiscUtils;
import send.toyou.schedulertaskmanager.domain.enums.DaysCronEnum;
import send.toyou.schedulertaskmanager.domain.enums.PeriodicityEnum;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ScheduledForgottenTask {
    private String second;
    private String minute;
    private String hour;
    private String dayOfMonth;
    private String month;
    private String year;
    private PeriodicityEnum periodicity;
    private int monthPeriodicityValue;
    private ArrayList<DaysCronEnum> daysToBeExecuted;
    private LocalDateTime lastExecutionDate;
    private LocalDateTime taskCreationDate;

    public static ScheduledForgottenTask fromScheduledDto(ScheduledTaskDto scheduledTaskDto) {
        String cronPart[] = scheduledTaskDto.getCron().split(" ");
        String dayOfWeek[] = cronPart[5].split(",");
        String periodicity[] = cronPart[4].split("/");
        String year = null;

        ArrayList<DaysCronEnum> allDaysWeek = new ArrayList<>(EnumSet.allOf(DaysCronEnum.class));
        ArrayList<DaysCronEnum> daysToExecute = null;

        Integer monthPeriodicityInteger = periodicity.length > 1 ? Integer.parseInt(periodicity[1]) : 0;

        PeriodicityEnum periodicityEnum = periodicity.length > 1 ?
                PeriodicityEnum.fromCronExpression(periodicity[1]) :
                PeriodicityEnum.fromCronExpression(periodicity[0]);

        if (cronPart.length > 6) {
            daysToExecute = dayOfWeek[0].length() > 1 ?
                    MiscUtils.convertArrayToArrayList(dayOfWeek) :
                    allDaysWeek;
            year = cronPart[6];
        } else {
            daysToExecute = allDaysWeek;
            year = cronPart[5];
        }

        return new ScheduledForgottenTask(
                cronPart[0],
                cronPart[1],
                cronPart[2],
                cronPart[3],
                periodicity[0],
                year,
                periodicityEnum,
                monthPeriodicityInteger,
                daysToExecute,
                scheduledTaskDto.getLastExecutionDate() == null ? null : LocalDateTime.parse(scheduledTaskDto.getLastExecutionDate()),
                scheduledTaskDto.getTaskCreationDate() == null ? null : LocalDateTime.parse(scheduledTaskDto.getTaskCreationDate())
        );
    }
}
