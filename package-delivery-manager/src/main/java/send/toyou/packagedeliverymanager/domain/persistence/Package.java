package send.toyou.packagedeliverymanager.domain.persistence;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

import java.time.LocalDateTime;



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
    private String name;
    private String status;
    private String dateCreated;

    public static Package fromPackageProcessedEvent(PackageProcessedEvent packageProcessedEvent) {
        return new Package(
            packageProcessedEvent.getId(),
            packageProcessedEvent.getSenderUserId(),
            packageProcessedEvent.getReceipterUserId(),
            packageProcessedEvent.getName(),
            packageProcessedEvent.getStatus(),
            packageProcessedEvent.getDateCreated() == null ? null : packageProcessedEvent.getDateCreated().toString()
        );
    }
}
