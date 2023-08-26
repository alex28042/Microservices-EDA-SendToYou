package send.toyou.packagemicroapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class User {
    @Test
    void testUser() {
        var user = new User();

        Assertions.assertNotNull(user);
    }
}
