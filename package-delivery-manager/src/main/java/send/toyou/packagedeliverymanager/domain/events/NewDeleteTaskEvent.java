package send.toyou.packagedeliverymanager.domain.events;

import lombok.*;
import send.toyou.packagedeliverymanager.domain.persistence.ScheduleTaskAbstract;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class NewDeleteTaskEvent extends ScheduleTaskAbstract {
}
