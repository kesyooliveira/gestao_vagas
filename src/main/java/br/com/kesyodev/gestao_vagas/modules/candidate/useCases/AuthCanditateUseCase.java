package br.com.kesyodev.gestao_vagas.modules.candidate.useCases;

import br.com.kesyodev.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.kesyodev.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.kesyodev.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
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
public class AuthCanditateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secret;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("Username/password incorrect");
                });

        var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if(!passwordMatches){
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresAt = Instant.now().plus(Duration.ofMinutes(120));

        var token = JWT.create()
                .withIssuer("kesyodev")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_at(expiresAt.atZone(ZoneId.systemDefault()))
                .build();

        return authCandidateResponse;

    }

}
