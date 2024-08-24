package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.configurations.security.utils.AuthTokenType;
import com.ernestocesario.myclothes.persistance.entities.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface JwtService {
    public void generateAccessToken(User user) throws JOSEException, ParseException;
    public void generateRefreshToken(User user) throws JOSEException, ParseException;

    public String getSubject(String token);
    public boolean validateAccessToken(String token);
    public boolean validateRefreshToken(String token);
}
