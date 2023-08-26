package send.toyou.packagemicroapi.infrastructure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.PackageServiceImpl;
import send.toyou.packagemicroapi.domain.Package;

@RestController
@RequestMapping("/api/package")
@Slf4j
public class PackageController {
    @Autowired
    private PackageServiceImpl packageService;

    @PostMapping
    public Mono<ResponseEntity<Package>> savePackage(@RequestBody Package packageToCreate) {
        return this.packageService.save(packageToCreate)
                .flatMap(packageCreated -> {
                    if (packageCreated.equals(packageToCreate)) {
                        return Mono.just(new ResponseEntity<>(packageCreated, HttpStatus.CREATED));
                    }

                    return Mono.just(new ResponseEntity<>(packageCreated, HttpStatus.BAD_REQUEST));
                })
                .onErrorContinue(this::handleError);
    }

    private void handleError(Throwable throwable, Object object) {
        log.error("Error API with error: {} and object: {}", throwable, object);
    }
}
