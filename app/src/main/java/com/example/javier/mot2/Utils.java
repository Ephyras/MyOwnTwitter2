package com.example.javier.mot2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Javier on 2/5/2018.
 */

public class Utils {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Taken from https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getSecurePassword(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(salt);
            //Get the hash's bytes
            byte[] bytes = md.digest(password.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //TODO: Manage this
        }
        return generatedPassword;
    }
}
