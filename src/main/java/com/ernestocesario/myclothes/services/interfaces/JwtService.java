package com.ernestocesario.myclothes.services.interfaces;

import com.ernestocesario.myclothes.persistance.entities.User;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface JwtService {
    public String generateAccessToken(User user) throws JOSEException, ParseException;
    public String generateRefreshToken(User user) throws JOSEException, ParseException;

    public String getSubject(String token);
    public boolean validateToken(String token);
}
