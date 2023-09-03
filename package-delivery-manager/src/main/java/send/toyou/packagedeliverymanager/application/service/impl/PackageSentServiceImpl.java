package send.toyou.packagedeliverymanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.packagedeliverymanager.application.service.PackageSentService;
import send.toyou.packagedeliverymanager.domain.events.PackageSentEvent;
import send.toyou.packagedeliverymanager.domain.persistence.Package;
import send.toyou.packagedeliverymanager.domain.repositories.PackageRepository;
import send.toyou.packagedeliverymanager.domain.repositories.UserRepository;
import send.toyou.packagedeliverymanager.domain.valueObjects.Address;

@Service
@Slf4j
public class PackageSentServiceImpl implements PackageSentService {
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Package> saveToDB(Package pack) {
        return this.packageRepository.save(pack)
                .flatMap(packageToUpdate -> {
                    if (pack == null) {
                        log.info("Package is not saved: {}", packageToUpdate);
                        return Mono.empty();
                    }

                    log.info("Package Saved: {}", packageToUpdate);
                    return Mono.just(packageToUpdate);
                });
    }

    @Override
    public Mono<PackageSentEvent> setDestinationPackage(PackageSentEvent packageSentEvent) {
        return this.userRepository.findById(packageSentEvent.getReceipterUserId())
                .doOnNext(user -> log.info("User Receipter: {}", user))
                .flatMap(user -> {
                    if (user == null) {
                        log.info("User not found for id: {}", packageSentEvent.getReceipterUserId());
                        return Mono.empty();
                    }
                    var userReceipterAddress = new Address();
                    userReceipterAddress.setFloor(user.getFloor());
                    userReceipterAddress.setStreet(user.getStreet());
                    userReceipterAddress.setNumber(user.getNumber());

                    packageSentEvent.setAddressDestination(userReceipterAddress);

                    return Mono.just(packageSentEvent);
                });
    }
}
