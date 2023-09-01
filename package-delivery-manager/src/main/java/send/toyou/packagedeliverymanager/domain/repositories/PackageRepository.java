package send.toyou.packagedeliverymanager.domain.repositories;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import send.toyou.packagedeliverymanager.domain.persistence.Package;

public interface PackageRepository extends R2dbcRepository<Package, String> {
}
