package send.toyou.packagedeliverymanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class PackageProcessedEvent extends PackageEventFields {
    public PackageProcessedEvent(String id, String senderUserId, String receipterUserId, String name, String status, LocalDateTime dateCreated, Address addressDestination) {
        super(id, senderUserId, receipterUserId, name, status, dateCreated, addressDestination);
    }

    public static PackageProcessedEvent fromPackage(Package pack) {
        return new PackageProcessedEvent(
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
