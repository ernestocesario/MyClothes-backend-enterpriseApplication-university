package com.ernestocesario.myclothes.services.implementations;

import com.ernestocesario.myclothes.services.interfaces.GoogleIdTokenVerifierService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleIdTokenVerifierServiceImpl implements GoogleIdTokenVerifierService {
    @Value("${google.clientId}")
    private String googleClientId;

    @Override
    public GoogleIdToken.Payload verifyIdToken(String idToken) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);

        if (googleIdToken == null)
            throw new SecurityException("Invalid ID Token");
        return googleIdToken.getPayload();
    }
}
