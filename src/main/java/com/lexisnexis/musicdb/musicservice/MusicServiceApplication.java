package com.lexisnexis.musicdb.musicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MusicServiceApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MusicServiceApplication.class);
    }
}
