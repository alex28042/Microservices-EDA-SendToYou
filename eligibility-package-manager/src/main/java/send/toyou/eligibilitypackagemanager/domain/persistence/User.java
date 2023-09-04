package send.toyou.eligibilitypackagemanager.domain.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Setter
@Getter
@Table(value = "user")
@ToString
public class User {
    private String id;
    private String email;
    private String street;
    private String floor;
    private String number;
}
