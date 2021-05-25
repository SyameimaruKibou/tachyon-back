package webserver.util;


import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordUtil {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean passwordJudge(String passwordString, String passwordSaveData) {
        return encoder.matches(passwordString, passwordSaveData);
    }

    public String passwordEncode(String passwordString) {
        return encoder.encode(passwordString);
    }
}
