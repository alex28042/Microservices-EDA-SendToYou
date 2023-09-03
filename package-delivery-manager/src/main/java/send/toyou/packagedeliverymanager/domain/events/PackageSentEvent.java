package send.toyou.packagedeliverymanager.domain.events;

import lombok.*;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class PackageSentEvent extends PackageEventFields {
    public PackageSentEvent(String id, String senderUserId, String receipterUserId, String name, String status, LocalDateTime dateCreated, Address addressDestination) {
        super(id, senderUserId, receipterUserId, name, status, dateCreated, addressDestination);
    }

    public static PackageSentEvent fromPackage(Package pack) {
        return new PackageSentEvent(
                pack.getId(),
                pack.getSenderUserId(),
                pack.getReceipterUserId(),
                pack.getName(),
                pack.getStatus(),
                pack.getDateCreated() == null ? null : LocalDateTime.parse(pack.getDateCreated()),
                null
        );
    }
}
