package send.toyou.packagedeliverymanager.domain.events;

import lombok.*;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class PackageEventFields {
    private String id;
    private String senderUserId;
    private String receipterUserId;
    private String name;
    private String status;
    private LocalDateTime dateCreated;
    private Address addressDestination;

}