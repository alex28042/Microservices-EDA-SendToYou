package send.toyou.packagemicroapi.infrastructure;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.useCases.impl.PackageServiceImpl;
import send.toyou.packagemicroapi.domain.persistence.Package;

@RestController
@RequestMapping("/api/package")
@Slf4j
public class PackageController {
    @Autowired
    private PackageServiceImpl packageService;

    @PostMapping
    public Mono<ResponseEntity<Package>> savePackage(@RequestBody Package packageToCreate) {
        return this.packageService.save(packageToCreate)
                .doOnNext(packageCreated -> log.info("Package Created: {}", packageCreated))
                .flatMap(packageCreated -> {
                    if (packageCreated.equals(packageToCreate)) {
                        return Mono.just(new ResponseEntity<>(packageCreated, HttpStatus.CREATED));
                    }

                    return Mono.just(new ResponseEntity<>(packageCreated, HttpStatus.BAD_REQUEST));
                });
    }
    
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Package>> getPackage(@RequestParam String id) {
        return this.packageService.getPackageById(id)
                .flatMap(pack -> {
                    if (pack != null) {
                        log.info("Package Finded: {}", pack);
                        return Mono.just(new ResponseEntity<>(pack, HttpStatus.CREATED));
                    }

                    return Mono.just(new ResponseEntity<>(pack, HttpStatus.BAD_REQUEST));
                });
    }
}
