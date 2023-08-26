package send.toyou.packagemicroapi.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.Package;
import send.toyou.packagemicroapi.domain.PackageRepository;

@Service
@Slf4j
public class PackageServiceImpl implements PackageService{
    @Autowired
    private PackageRepository packageRepository;


    @Override
    public Mono<Package> save(Package packageToCreate) {
        return this.packageRepository.findById(packageToCreate.getId())
                .doOnNext(packageFinded -> log.info("Package in DB with id: {}", packageFinded.getId()))
                .flatMap(packageFinded -> {
                    if (packageFinded != null) {
                        return Mono.just(packageFinded);
                    }

                    return Mono.empty();
                });
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
