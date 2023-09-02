package send.toyou.packagedeliverymanager.domain.events;

import lombok.*;
import send.toyou.packagedeliverymanager.domain.persistence.ScheduleTaskAbstract;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class NewDeleteTaskEvent extends ScheduleTaskAbstract {
}
