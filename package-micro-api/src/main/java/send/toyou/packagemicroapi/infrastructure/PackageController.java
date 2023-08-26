package send.toyou.packagemicroapi.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.application.PackageServiceImpl;
import send.toyou.packagemicroapi.domain.Package;

@RestController
@RequestMapping("/api/package")
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
                });
    }
}
