package send.toyou.mailmanager.domain.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class Address {
    private String street;
    private String floor;
    private String number;
}
