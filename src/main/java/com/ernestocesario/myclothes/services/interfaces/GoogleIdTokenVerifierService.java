package com.ernestocesario.myclothes.services.interfaces;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleIdTokenVerifierService {
    public GoogleIdToken.Payload verifyIdToken(String idToken) throws GeneralSecurityException, IOException;
}
