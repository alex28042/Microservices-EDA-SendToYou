package send.toyou.authservice.domain.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import send.toyou.authservice.domain.persistence.User;

public interface UserRepository extends R2dbcRepository<User, String> {
    @Query("SELECT * from users u " +
            "Where u.email = $1 ")
    public Mono<User> findByEmail(String email);

}
