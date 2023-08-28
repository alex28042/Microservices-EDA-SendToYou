package send.toyou.packagedeliverymanager.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NewPackageEvent {
    private String id;
    private String senderUserId;
    private String receipterUserId;
    private String name;
    private PackageStatusEnum status;
    private LocalDateTime dateCreated;
    public static NewPackageEvent fromPackage(Package pack) {
        return new NewPackageEvent(
                pack.getId(),
                pack.getSenderUserId(),
                pack.getReceipterUserId(),
                pack.getName(),
                pack.getStatus(),
                pack.getDateCreated()
        );
    }
}

