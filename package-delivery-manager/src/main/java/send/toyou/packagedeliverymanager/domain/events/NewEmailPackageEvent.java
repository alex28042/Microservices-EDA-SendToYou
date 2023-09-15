package send.toyou.packagedeliverymanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class NewEmailPackageEvent {
    private String emailReceipter;
    private PackageStatusEnum packageStatusEnum;
}
