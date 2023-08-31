package send.toyou.schedulertaskmanager.domain.events;

import lombok.*;
import send.toyou.schedulertaskmanager.domain.persistence.ScheduleTaskAbstract;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@Data
public class NewDeleteTaskEvent extends ScheduleTaskAbstract {

}
