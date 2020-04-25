package org.physicscode.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bson.internal.Base64;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class SecurityUtils {

    private static final String HMAC_SHA_256 = "HmacSHA256";
    private static final String ISSUER = "e-boost-api  -  Distressed Awaken Panda";
    private final Algorithm signatureAlgorithm;
    private final Mac hmacSHA256;
    private final Clock clock;

    public SecurityUtils (@Value("auth.hashing-secret") String hashingSecret,
                          @Value("auth.signing-secret") String signignSecret,
                          Clock clock) throws NoSuchAlgorithmException, InvalidKeyException {

        this.clock = clock;
        this.hmacSHA256 = Mac.getInstance(HMAC_SHA_256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashingSecret.getBytes(UTF_8), HMAC_SHA_256);
        this.hmacSHA256.init(secretKeySpec);
        this.signatureAlgorithm = Algorithm.HMAC256(signignSecret.getBytes());
    }

    public String hashPass(String password) {

        Mac mac = cloneMac();
        byte[] bytes = mac.doFinal(Base64.decode(password));
        return new String(Hex.encode(bytes)).toLowerCase();
    }


    private Mac cloneMac() {

        try {
            return (Mac) hmacSHA256.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed cloning Mac!", e);
        }
    }

    public String createToken(EBoostAuthenticationUser savedUser) {

        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(clock.instant()))
                .withSubject(savedUser.getUserId())
                .withClaim("email", savedUser.getEmail())
                .withClaim("type", savedUser.getUserType().toString())
                .withExpiresAt(Date.from(clock.instant().plus(15, ChronoUnit.DAYS)))
                .sign(signatureAlgorithm);
    }

    public Algorithm obtainAlgorithm() {

        return this.signatureAlgorithm;
    }
}
