package com.mosaics.mosaicsback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class MosaicsBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(MosaicsBackApplication.class, args);
    }

}
