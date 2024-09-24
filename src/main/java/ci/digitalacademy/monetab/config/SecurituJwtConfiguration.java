package ci.digitalacademy.monetab.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static ci.digitalacademy.monetab.utils.SecurityUtils.AUTHORITIES_KEY;
import static ci.digitalacademy.monetab.utils.SecurityUtils.JWT_ALGORITHM;

@Configuration
public class SecurituJwtConfiguration {

    @Value("${security.authentication.jwt.base64-secret}")
    private String jwtKey;

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withSecretKey(getSecretKey()).macAlgorithm(JWT_ALGORITHM).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter granteAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        granteAuthoritiesConverter.setAuthorityPrefix("");
        granteAuthoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_KEY);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(granteAuthoritiesConverter);
        return jwtAuthenticationConverter;

    }

    private SecretKey getSecretKey() {
       byte[] keyBytes = Base64.from(jwtKey).decode();
       return new SecretKeySpec(keyBytes,0,keyBytes.length,JWT_ALGORITHM.getName());
    }
}
