package com.library.component;


import java.util.Base64;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryPBKDF2Encoder
 * Component
 */
@Component
public class LibraryPBKDF2Encoder implements PasswordEncoder {

    @Value("${itclMmsApplication.password.encoder.secret}")
    private String secret;

    @Value("${itclMmsApplication.password.encoder.iteration}")
    private Integer iteration;

    @Value("${itclMmsApplication.password.encoder.key-length}")
    private Integer keyLength;

    /**
     * More info (https://www.owasp.org/index.php/Hashing_Java) 404 :(
     * @param cs password
     * @return encoded password
     */
    @Override
    public String encode(CharSequence cs) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                                            .generateSecret(new PBEKeySpec(cs.toString().toCharArray(), secret.getBytes(), iteration, keyLength))
                                            .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        return encode(cs).equals(string);
    }

    public String base64(String password){
        return Base64.getEncoder().withoutPadding().encodeToString(password.getBytes(StandardCharsets.UTF_8));
    }
}
