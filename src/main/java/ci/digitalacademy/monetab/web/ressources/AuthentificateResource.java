package ci.digitalacademy.monetab.web.ressources;

import ci.digitalacademy.monetab.services.dto.JwtTokenDTO;
import ci.digitalacademy.monetab.services.dto.LoginDTO;
import ci.digitalacademy.monetab.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthentificateResource {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${security.authentication.jwt.token-validity-in-seconds:0}")
    private Long tokenValidityInSeconde;

    @Value("${security.authentication.jwt.token-validity-in-seconds-for-remember-me:0}")
    private Long tokenValidityInSecondeForRmenberMe;

    private final JwtEncoder jwtEncoder;

    @PostMapping("/authenticate")
    public JwtTokenDTO authorize(@RequestBody  LoginDTO login){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                login.getUser_name(),
                login.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = createToken(authentication,true);
        return new JwtTokenDTO(jwt);

    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(""));
        Instant now = Instant.now();
        Instant validity;
        if(rememberMe){
            validity = now.plus(this.tokenValidityInSecondeForRmenberMe, ChronoUnit.SECONDS);
        }else {
            validity = now.plus(this.tokenValidityInSeconde, ChronoUnit.SECONDS);
        }
        //@formayyer::off
        JwtClaimsSet claim = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim(SecurityUtils.AUTHORITIES_KEY, authorities)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claim)).getTokenValue();
    }
}
