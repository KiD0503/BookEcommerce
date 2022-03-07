package com.book.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

//To test and show about bCrypt code
public class PasswordEncoderTest {
    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "hieu2111";
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
        System.out.println(encodePassword);
        boolean matches = bCryptPasswordEncoder.matches(rawPassword,encodePassword);
        assertThat(matches).isTrue();

    }
}
