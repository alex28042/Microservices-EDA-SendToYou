package send.toyou.packagemicroapi.domain.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.packagemicroapi.domain.enums.PackageStatusEnum;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "package")
public class Package {
    private String id;
    private String senderUserId;
    private String receipterUserId;
    private int packageSize;
    private String name;
    private String status;
    private String dateCreated;


}
