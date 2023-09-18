package send.toyou.packagemicroapi.domain.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import send.toyou.packagemicroapi.domain.valueObjects.Address;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(value = "user")
public class User {
    private String id;
    private String email;
    private Address address;
}

