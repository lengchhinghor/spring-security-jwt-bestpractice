package com.chhinghor.hrd.chhinghorjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class ChhinghorJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChhinghorJwtApplication.class, args);
    }

}
