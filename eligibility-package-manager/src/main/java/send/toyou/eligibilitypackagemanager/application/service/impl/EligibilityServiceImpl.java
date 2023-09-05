package send.toyou.eligibilitypackagemanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import send.toyou.eligibilitypackagemanager.application.service.EligibilityService;
import send.toyou.eligibilitypackagemanager.domain.constants.PackageConstants;
import send.toyou.eligibilitypackagemanager.domain.enums.PackageStatusEnum;
import send.toyou.eligibilitypackagemanager.domain.events.NewPackageEvent;
import send.toyou.eligibilitypackagemanager.domain.persistence.Package;
import send.toyou.eligibilitypackagemanager.domain.repositories.PackageRepository;

@Service
@Slf4j
public class EligibilityServiceImpl implements EligibilityService {
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public Mono<Package> processEligiblePackage(NewPackageEvent newPackageEvent) {
        if (!this.isEligiblePackage(newPackageEvent)) {
            newPackageEvent.setStatus(PackageStatusEnum.NO_ELIGIBLE.name());
            log.info("Package No Eligible: {}", newPackageEvent);
        }

        var pack = Package.fromNewPackageEvent(newPackageEvent);

        return this.packageRepository.save(pack);
    }

    private boolean isEligiblePackage(NewPackageEvent newPackageEvent) {
        if (
            newPackageEvent.getPackageSize() > PackageConstants.MAX_SIZE ||
            newPackageEvent.getPackageSize() < PackageConstants.MIN_SIZE
        ) {
            return false;
        }

        return newPackageEvent.getSenderUserId() != null && newPackageEvent.getReceipterUserId() != null;
    }
}
