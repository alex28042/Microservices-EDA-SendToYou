package send.toyou.packagedeliverymanager.domain.repositories;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import send.toyou.packagedeliverymanager.domain.persistence.User;

@Repository
public interface UserRepository extends R2dbcRepository<User, String> {

}
