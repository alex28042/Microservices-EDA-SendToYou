package send.toyou.authservice.domain.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PackageRepository extends R2dbcRepository<Package, String> {
    @Query("SELECT * from package where id = $1")
    Mono<Package> findByIdPackage(String id);
}
