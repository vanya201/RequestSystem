package org.common.seeder;


import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.common.service.RSAKeyService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RSAKeySeeder {
    private final RSAKeyService rsaKeyService;

    @PostConstruct
    public void init() {
        rsaKeyService.createKeyPair();
    }
}
