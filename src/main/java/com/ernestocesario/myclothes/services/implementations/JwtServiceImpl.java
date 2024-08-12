package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.persistance.entities.User;
import com.ernestocesario.myclothes.services.interfaces.JwtService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret.signature.key}")
    private String secretKey;

    @Value("${jwt.secret.encryption.key}")
    private String encryptionKey;

    @Value("${jwt.access.token.expiration.time}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh.token.expiration.time}")
    private long refreshTokenExpirationTime;


    @Override
    public String generateAccessToken(User user) throws JOSEException, ParseException {
        return generateToken(user, accessTokenExpirationTime);
    }

    @Override
    public String generateRefreshToken(User user) throws JOSEException, ParseException {
        return generateToken(user, refreshTokenExpirationTime);
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
    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = decryptToken(token);
            MACVerifier macVerifier = new MACVerifier(secretKey);

            return signedJWT.verify(macVerifier) && !isTokenExpired(signedJWT);
        }
        catch (ParseException | JOSEException e) {
            return false;
        }
    }



    //private methods

    private String generateToken(User user, long expirationTime) throws JOSEException, ParseException {
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                .build();

        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);

        signedJWT.sign(new MACSigner(secretKey));

        return encryptToken(signedJWT);
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
