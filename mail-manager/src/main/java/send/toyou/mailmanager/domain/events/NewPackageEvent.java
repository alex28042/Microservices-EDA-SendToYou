package send.toyou.mailmanager.domain.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import send.toyou.mailmanager.domain.persistence.Package;
import send.toyou.mailmanager.domain.valueObjects.Address;

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

