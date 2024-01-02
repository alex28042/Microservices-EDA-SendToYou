package send.toyou.authservice.application.services;

public interface JwtService {
    String generateToken(String userId);
}
