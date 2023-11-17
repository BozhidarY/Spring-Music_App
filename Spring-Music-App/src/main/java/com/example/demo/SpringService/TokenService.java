package com.example.demo.SpringService;

import com.example.demo.Entities.UserType;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;
//    private static final String SECRET_KEY = generateSecretKey();

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateJwt(Authentication authentication){
        Instant now = Instant.now();

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(authentication.getName())
                .claim("roles", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

//    public static String generateJwtToken(String subject, Set<String> roles, long expirationTimeMillis) throws JOSEException, JOSEException {
//        JWSSigner signer = new MACSigner(SECRET_KEY);
//
//        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                .subject(subject)
//                .expirationTime(new Date(System.currentTimeMillis() + expirationTimeMillis))
//                .claim("authorities", roles)
//                .build();
//
//        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
//        signedJWT.sign(signer);
//
//        return signedJWT.serialize();
//    }
//
//    private static String generateSecretKey() {
//        // Use a secure method to generate a secret key
//        byte[] secretKey = new byte[32];
//        // Replace this with a secure method for generating random bytes, such as SecureRandom
//        // Example: SecureRandom.getInstanceStrong().nextBytes(secretKey);
//        // Here, we use a simple example for demonstration purposes only
//        for (int i = 0; i < secretKey.length; i++) {
//            secretKey[i] = (byte) (i + 1);
//        }
//        return Base64.getEncoder().encodeToString(secretKey);
//    }

}
