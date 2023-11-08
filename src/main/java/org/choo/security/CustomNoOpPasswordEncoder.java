package org.choo.security;

import lombok.extern.log4j.Log4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        log.warn("before encode : " + rawPassword);
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        log.warn("matches: " + rawPassword + ":" + encodePassword);
        return rawPassword.toString().equals(encodePassword);
    }
}
