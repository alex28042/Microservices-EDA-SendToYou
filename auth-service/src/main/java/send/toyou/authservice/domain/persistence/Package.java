package send.toyou.authservice.domain.persistence;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

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
