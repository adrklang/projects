package com.lhstack.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5Utils {
    public static String encode(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        byte[] digest = messageDigest.digest((password + salt).getBytes());
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(digest);
    }
}
