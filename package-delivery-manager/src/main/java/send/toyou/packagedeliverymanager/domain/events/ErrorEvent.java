package send.toyou.packagedeliverymanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ErrorEvent {
    private String description;
    private String errorFrom;
    private Object object;
}
