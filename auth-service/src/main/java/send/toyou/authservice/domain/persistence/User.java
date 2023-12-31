package send.toyou.authservice.domain.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.authservice.domain.valueObjects.Address;


@NoArgsConstructor
@Setter
@Getter
@Table(value = "user")
public class User {
    private String id;
    private String email;
    private Address address;
}

