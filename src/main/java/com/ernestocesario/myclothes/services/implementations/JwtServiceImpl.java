package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.configurations.security.utils.AuthTokenType;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.persistance.repositories.UserRepository;
import com.ernestocesario.myclothes.services.interfaces.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;

import static com.ernestocesario.myclothes.configurations.security.utils.AuthTokenType.ACCESS_TOKEN;
import static com.ernestocesario.myclothes.configurations.security.utils.AuthTokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserRepository userRepository;

    @Value("${jwt.secret.signature.key}")
    private String secretKey;

    @Value("${jwt.secret.encryption.key}")
    private String encryptionKey;

    @Value("${jwt.access.token.expiration.time}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;


    @Override
    @Transactional
    public String generateAccessToken(User user) throws JOSEException, ParseException {
        return generateToken(user, ACCESS_TOKEN, accessTokenExpirationTime);
    }

    @Override
    @Transactional
    public String generateRefreshToken(User user) throws JOSEException, ParseException {
        return generateToken(user, REFRESH_TOKEN, refreshTokenExpirationTime);
    }

    @Override
    public String getSubject(String token) {
        try {

            return decryptToken(token).getJWTClaimsSet().getSubject();
        }
        catch (JOSEException | ParseException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean validateAccessToken(String token) {
        return validateToken(token, ACCESS_TOKEN);
    }

    @Override
    @Transactional
    public boolean validateRefreshToken(String token) {
        return validateToken(token, REFRESH_TOKEN);
    }



    //private methods
    private String generateToken(User user, AuthTokenType authTokenType, long expirationTime) throws JOSEException, ParseException {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();

        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);

        signedJWT.sign(new MACSigner(secretKey));

        switch (authTokenType) {
            case ACCESS_TOKEN -> user.setAccessToken(signedJWT.serialize());
            case REFRESH_TOKEN -> user.setRefreshToken(signedJWT.serialize());
        }
        userRepository.save(user);

        return encryptToken(signedJWT);
    }

    private boolean validateToken(String token, AuthTokenType authTokenType) {
        try {
            SignedJWT signedJWT = decryptToken(token);
            MACVerifier macVerifier = new MACVerifier(secretKey);

            if (!signedJWT.verify(macVerifier) || isTokenExpired(signedJWT))
                return false;

            String email = signedJWT.getJWTClaimsSet().getSubject();
            User user = userRepository.findByEmail(email);

            if (user == null)
                return false;

            return switch (authTokenType) {
                case ACCESS_TOKEN -> user.getAccessToken().equals(signedJWT.serialize());
                case REFRESH_TOKEN -> user.getRefreshToken().equals(signedJWT.serialize());
            };
        }
        catch (ParseException | JOSEException e) {
            return false;
        }
    }

    private String encryptToken(SignedJWT signedJWT) throws ParseException, JOSEException {
        SecretKey key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        EncryptedJWT encryptedJWT = new EncryptedJWT(
                new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A256GCM),
                signedJWT.getJWTClaimsSet()
        );

        encryptedJWT.encrypt(new DirectEncrypter(key));

        return encryptedJWT.serialize();
    }

    private SignedJWT decryptToken(String encryptedToken) throws ParseException, JOSEException {
        SecretKey key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        EncryptedJWT encryptedJWT = EncryptedJWT.parse(encryptedToken);

        encryptedJWT.decrypt(new DirectDecrypter(key));

        return encryptedJWT.getPayload().toSignedJWT();
    }

    private boolean isTokenExpired(SignedJWT signedJWT) throws ParseException {
        return signedJWT.getJWTClaimsSet().getExpirationTime().before(new Date());
    }
}
