package util;

import java.security.NoSuchAlgorithmException;

public class Util {
    public static String hashString(String text) throws NoSuchAlgorithmException {
        java.security.MessageDigest md =
                java.security.MessageDigest.getInstance("SHA-512");

        byte[] bytes = md.digest(text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
