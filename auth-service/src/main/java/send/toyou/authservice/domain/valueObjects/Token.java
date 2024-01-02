package send.toyou.authservice.domain.valueObjects;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Token {
    private String jwtToken;
}
