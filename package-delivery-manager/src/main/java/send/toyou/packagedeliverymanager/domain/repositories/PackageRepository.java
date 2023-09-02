package send.toyou.packagedeliverymanager.domain.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

public interface PackageRepository extends R2dbcRepository<Package, String> {
    @Override
    @Query("select * from package where id = $1")
    Mono<Package> findById(String s);

    @Override
    <S extends Package> Mono<S> save(S entity);
}
