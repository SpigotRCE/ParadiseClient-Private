package io.github.spigotrce.paradiseclientprivate.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HWID {
    public static String getHWID() {
        return
                md5Hash(
                        System.getenv("os")
                                + System.getProperty("os.name")
                                + System.getProperty("os.arch")
                                + System.getProperty("user.name")
                                + System.getenv("SystemRoot")
                                + System.getenv("HOMEDRIVE")
                                + System.getenv("PROCESSOR_LEVEL")
                                + System.getenv("PROCESSOR_REVISION")
                                + System.getenv("PROCESSOR_IDENTIFIER")
                                + System.getenv("PROCESSOR_ARCHITECTURE")
                                + System.getenv("PROCESSOR_ARCHITEW6432")
                                + System.getenv("NUMBER_OF_PROCESSORS")
                );
    }

    private static String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
