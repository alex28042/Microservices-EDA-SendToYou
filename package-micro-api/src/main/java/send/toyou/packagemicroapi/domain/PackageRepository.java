package send.toyou.packagemicroapi.domain;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepository extends R2dbcRepository<Package, Long> {

}
