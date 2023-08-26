package send.toyou.packagemicroapi.domain.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import send.toyou.packagemicroapi.domain.persistence.Package;

@Repository
public interface PackageRepository extends R2dbcRepository<Package, Long> {

}
