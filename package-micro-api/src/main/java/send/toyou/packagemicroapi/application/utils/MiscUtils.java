package send.toyou.packagemicroapi.application.utils;

import reactor.core.publisher.Mono;
import send.toyou.packagemicroapi.domain.persistence.Package;

import java.util.UUID;

public class MiscUtils {
    public static Mono<Package> parsePackageWithUuid(Package pack) {
        return Mono.just(UUID.randomUUID())
                .flatMap(uuid -> {
                    pack.setId(uuid.toString());

                    return Mono.just(pack);
                });
    }
}
