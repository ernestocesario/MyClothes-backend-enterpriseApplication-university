package com.ernestocesario.myclothes.configurations.security.authorization;

import com.ernestocesario.myclothes.exceptions.auth.InvalidAuthorizationException;
import com.ernestocesario.myclothes.persistance.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class AuthorizationChecker {
    private AuthorizationChecker() {}  //no instantiation

    /*
    //only for testing purposes
    static {
        ConfigurableEnvironment environment = new StandardEnvironment(); // Crea un ambiente Spring
        MutablePropertySources propertySources = environment.getPropertySources();
        try {
            propertySources.addLast(new ResourcePropertySource("classpath:application.properties")); // Carica le proprietà
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        testing = Boolean.TRUE.equals(environment.getProperty("testing", Boolean.class)); // Ottieni il valore di 'efg'
    }

    private static final boolean testing;
     */

    public static void check(AuthorizationTest authorizationTest, User user, Object... objects) {
        /*if (testing)
            return;
        */

        try {
            if (!authorizationTest.test(user, objects))
                throw new InvalidAuthorizationException();
        }
        catch (Exception e) {
            throw new InvalidAuthorizationException();
        }
    }
}
