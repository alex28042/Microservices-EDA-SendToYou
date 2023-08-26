package send.toyou.packagemicroapi.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.Package;
import send.toyou.packagemicroapi.domain.PackageRepository;

@Service
public class PackageServiceImpl implements PackageService{
    private PackageRepository packageRepository;


    @Override
    public Mono<Package> save(Package packageToCreate) {
        return null;
    }

    @Override
    public Mono<Package> getPackageById(Long id) {
        return null;
    }

    @Override
    public Flux<Package> getAllPackages() {
        return null;
    }

    @Override
    public Mono<Package> deletePackage(Package packageToCreate) {
        return null;
    }
}
