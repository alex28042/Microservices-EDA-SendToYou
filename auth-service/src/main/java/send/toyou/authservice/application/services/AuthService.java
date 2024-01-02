package send.toyou.authservice.application.services;


import reactor.core.publisher.Mono;
import send.toyou.authservice.domain.persistence.User;
import send.toyou.authservice.domain.valueObjects.Token;

public interface AuthService {
    Mono<Token> authenticateUser(User user);
    Mono<Token> loginUser(User user);
}
