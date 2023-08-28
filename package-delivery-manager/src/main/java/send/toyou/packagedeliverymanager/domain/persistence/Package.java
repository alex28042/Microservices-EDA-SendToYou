package send.toyou.packagedeliverymanager.domain.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.packagedeliverymanager.domain.enums.PackageStatusEnum;

import java.time.LocalDateTime;



@Getter
@NoArgsConstructor
@Setter
@Table(value = "package")
public class Package {
    private String id;
    private String senderUserId;
    private String receipterUserId;
    private String name;
    private PackageStatusEnum status;
    private LocalDateTime dateCreated;
}
