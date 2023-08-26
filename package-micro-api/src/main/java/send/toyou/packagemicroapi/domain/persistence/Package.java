package send.toyou.packagemicroapi.domain.persistence;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "package")
public class Package {
    private String id;
    private String name;
}
