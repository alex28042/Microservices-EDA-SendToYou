package send.toyou.authservice.application.utils;

import send.toyou.authservice.domain.valueObjects.Token;

public class MiscUtils {
    public static Token StringToToken(String tokenString) {
        var token = new Token();

        token.setJwtToken(tokenString);

        return token;
    }
}
