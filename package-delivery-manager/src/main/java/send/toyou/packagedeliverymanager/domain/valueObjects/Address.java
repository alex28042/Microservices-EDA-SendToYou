package send.toyou.packagedeliverymanager.domain.valueObjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Address {
    private String street;
    private String floor;
    private String number;
}
