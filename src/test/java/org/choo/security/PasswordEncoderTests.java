package org.choo.security;

import lombok.extern.log4j.Log4j;
import org.choo.config.RootConfig;
import org.choo.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class, SecurityConfig.class})
public class PasswordEncoderTests {
    @Autowired
    private PasswordEncoder pwEncoder;

    @Test
    public void testEncode() {
        String str = "member";
        String enStr = pwEncoder.encode(str);
        log.info(enStr);
    }
}
