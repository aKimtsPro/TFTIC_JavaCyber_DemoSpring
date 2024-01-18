package be.tftic.spring.demo.api.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Getter @Setter
@ConfigurationProperties("app.jwt")
@ToString
public class JwtProperties {

    private int expiresAt = 86400;
    private byte[] secret = "s@lt_s3Cr3|_s@lt".getBytes(StandardCharsets.UTF_8);
    private String headerName = "Authorization";

    public void setSecret(String secret){
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
    }

}
