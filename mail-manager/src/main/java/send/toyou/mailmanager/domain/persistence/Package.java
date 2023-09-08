package send.toyou.mailmanager.domain.persistence;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;



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
}
