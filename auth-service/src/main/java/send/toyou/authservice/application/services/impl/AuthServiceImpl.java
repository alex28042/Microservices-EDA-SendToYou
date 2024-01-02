package send.toyou.authservice.application.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import send.toyou.authservice.application.services.AuthService;
import send.toyou.authservice.application.services.JwtService;
import send.toyou.authservice.application.utils.MiscUtils;
import send.toyou.authservice.domain.persistence.User;
import send.toyou.authservice.domain.repositories.UserRepository;
import send.toyou.authservice.domain.valueObjects.Token;

public class AuthServiceImpl implements AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<Token> authenticateUser(User user) {
        return this.userRepository.findByEmail(user.getEmail())
                .flatMap(userFound -> {
                    if (userFound == null) {
                        return Mono.just(user);
                    }

                    return Mono.empty();
                })
                .map(User::getId)
                .map(jwtService::generateToken)
                .map(MiscUtils::StringToToken);
    }

    @Override
    public Mono<Token> loginUser(User user) {
        return null;
    }
}
