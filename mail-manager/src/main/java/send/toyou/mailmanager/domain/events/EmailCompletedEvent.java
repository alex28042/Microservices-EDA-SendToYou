package send.toyou.mailmanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import send.toyou.mailmanager.domain.enums.PackageStatusEnum;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmailCompletedEvent {
    private String emailReceipter;
    private String message;
    private boolean sent;
}
