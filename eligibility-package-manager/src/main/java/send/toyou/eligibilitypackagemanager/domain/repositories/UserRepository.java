package send.toyou.eligibilitypackagemanager.domain.repositories;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import send.toyou.eligibilitypackagemanager.domain.persistence.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, String> {
    @Override
    @Query("SELECT * FROM \"user\" where id = $1")
    Mono<User> findById(String s);
}
