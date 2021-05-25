package webserver.util;

import java.util.UUID;

public class CodeUtil {
    public static String getUUID_16() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = - hashCodeV;
        }
        return String.format("%015d", hashCodeV);
    }
}
