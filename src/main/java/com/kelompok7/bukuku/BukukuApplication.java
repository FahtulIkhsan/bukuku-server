package com.kelompok7.bukuku;

import com.kelompok7.bukuku.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class BukukuApplication {

	public static void main(String[] args) {
		SpringApplication.run(BukukuApplication.class, args);
	}

}
