package com.his.common.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("123456");
        System.out.println("BCrypt hash for '123456': " + hash);
        System.out.println("Verify: " + encoder.matches("123456", hash));
    }
}
