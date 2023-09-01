package send.toyou.packagedeliverymanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.PackageProcessedService;
import send.toyou.packagedeliverymanager.domain.events.NewPackageEvent;
import send.toyou.packagedeliverymanager.domain.events.PackageProcessedEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.repositories.PackageRepository;
import send.toyou.packagedeliverymanager.domain.repositories.UserRepository;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

@Service
@Slf4j
public class PackageProcessedServiceImpl implements PackageProcessedService {
    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Package> save(Package packageToUpdate) {
        return this.packageRepository.save(packageToUpdate)
                .flatMap(pack -> {
                    if (pack == null) {
                        log.info("Package is not saved: {}", packageToUpdate);
                        return Mono.empty();
                    }

                    log.info("Package Saved: {}", pack);
                    return Mono.just(pack);
                });
    }

    @Override
    public Mono<PackageProcessedEvent> getPackageDestinationAddress(PackageProcessedEvent pack) {
        return this.userRepository.findById(pack.getReceipterUserId())
                .doOnNext(user -> log.info("User Receipter: {}", user))
                .flatMap(user -> {
                    if (user == null) {
                        log.info("User not found for id: {}", pack.getReceipterUserId());
                        return Mono.empty();
                    }
                    var userReceipterAddress = new Address();
                    userReceipterAddress.setFloor(user.getFloor());
                    userReceipterAddress.setStreet(user.getStreet());
                    userReceipterAddress.setNumber(user.getNumber());

                    pack.setAddressDestination(userReceipterAddress);
                    return Mono.just(pack);
                });
    }
}
