package send.toyou.authservice.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import send.toyou.authservice.application.services.AuthService;
import send.toyou.authservice.domain.persistence.User;
import send.toyou.authservice.domain.valueObjects.Token;

@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/sign-up")
    public Mono<ResponseEntity<Token>> authenticateUser(@RequestBody User user) {
        return this.authService.authenticateUser(user)
                .map(token -> new ResponseEntity<>(token, HttpStatusCode.valueOf(201)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatusCode.valueOf(404))));
    }
}
