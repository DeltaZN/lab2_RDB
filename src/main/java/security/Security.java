package security;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Security {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(salt);
    }

    @SneakyThrows
    public static String hashPassword(String password, String salt) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        Base64.Encoder enc = Base64.getEncoder();
        Base64.Decoder dec = Base64.getDecoder();
        md.update(dec.decode(salt));
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return enc.encodeToString(hashedPassword);
    }
}
