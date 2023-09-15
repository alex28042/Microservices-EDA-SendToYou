package send.toyou.packagedeliverymanager.domain.events;

import lombok.*;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class NewPackageEvent extends PackageEventFields {

    public NewPackageEvent(String id, String senderUserId, String receipterUserId, int packageSize, String name, String status, LocalDateTime dateCreated, Address addressDestination) {
        super(id, senderUserId, receipterUserId, packageSize, name, status, dateCreated, addressDestination);
    }

    public static NewPackageEvent fromPackage(Package pack) {
        return new NewPackageEvent(
                pack.getId(),
                pack.getSenderUserId(),
                pack.getReceipterUserId(),
                pack.getPackageSize(),
                pack.getName(),
                pack.getStatus(),
                pack.getDateCreated() == null ? null : LocalDateTime.parse(pack.getDateCreated()),
                null
        );
    }
}

