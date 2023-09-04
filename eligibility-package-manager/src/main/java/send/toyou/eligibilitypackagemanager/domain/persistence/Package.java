package send.toyou.eligibilitypackagemanager.domain.persistence;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "package")
@ToString
public class Package {
    private String id;
    private String senderUserId;
    private String receipterUserId;
    private int packageSize;
    private String name;
    private String status;
    private String dateCreated;

    public static Package fromNewPackageEvent(NewPackageEvent newPackageEvent) {
        return new Package(
            newPackageEvent.getId(),
            newPackageEvent.getSenderUserId(),
            newPackageEvent.getReceipterUserId(),
            newPackageEvent.getPackageSize(),
            newPackageEvent.getName(),
            newPackageEvent.getStatus(),
            newPackageEvent.getDateCreated() == null ? null : newPackageEvent.getDateCreated().toString()
        );
    }

}
