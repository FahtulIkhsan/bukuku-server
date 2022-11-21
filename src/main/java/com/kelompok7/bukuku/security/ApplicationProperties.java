package com.kelompok7.bukuku.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
public class ApplicationProperties {
    private String port;
    private String address;

    @Autowired
    public ApplicationProperties(@Value("${server.port:8080}") String propPort){
        this.port = propPort;
        this.address = "103.31.39.143";
    }
}
