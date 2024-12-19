package br.com.kesyodev.gestao_vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String objectToJSON(Object obj){
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static String generateToken(UUID iDCompany, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var expiresAt = Instant.now().plus(Duration.ofMinutes(120));

        var token = JWT.create()
                .withIssuer("kesyodev")
                .withExpiresAt(expiresAt) //Tempo de expiração do token 2 hrs
                .withSubject(iDCompany.toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        return token;
    }

}
