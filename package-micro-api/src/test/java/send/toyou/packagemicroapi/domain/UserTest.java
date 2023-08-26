package send.toyou.packagemicroapi.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import send.toyou.packagemicroapi.domain.persistence.User;

public class UserTest {
    @Test
    void testUser() {
        var user = new User();
        user.setId(23L);
        user.setName("Test");

        Assertions.assertNotNull(user);
    }
}
