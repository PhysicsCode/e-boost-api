package org.physicscode;

import org.physicscode.config.properties.AssetStoreProperties;
import org.physicscode.service.client.AssetStoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@SpringBootApplication
@EnableConfigurationProperties(AssetStoreProperties.class)
public class EBoostApiLauncher {

    public static void main(String[] args) {

        SpringApplication.run(EBoostApiLauncher.class, args);
    }
}
