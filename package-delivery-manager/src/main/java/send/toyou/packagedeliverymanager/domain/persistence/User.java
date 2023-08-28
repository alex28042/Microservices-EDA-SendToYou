package send.toyou.packagedeliverymanager.domain.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Setter
@Getter
@Table(value = "user")
public class User {
    private String id;
}
