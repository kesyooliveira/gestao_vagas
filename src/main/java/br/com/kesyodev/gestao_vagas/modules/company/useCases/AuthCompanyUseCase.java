package br.com.kesyodev.gestao_vagas.modules.company.useCases;

import br.com.kesyodev.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.kesyodev.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.kesyodev.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                ()->{
                    throw new UsernameNotFoundException("Username and/or password icorret");
                }
        );
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresAt = Instant.now().plus(Duration.ofMinutes(120));

        var token = JWT.create()
                .withIssuer("kesyodev")
                .withExpiresAt(expiresAt) //Tempo de expiração do token 2 hrs
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyRepsonseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_at(expiresAt.atZone(ZoneId.systemDefault()))
                .build();

        return authCompanyRepsonseDTO;
    }

}
