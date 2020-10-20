package br.com.beertech.fusion.config;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Configuration
public class JwtConfiguration {

    public static String getjwtSecret() {
        return "grupofusion";
    }

    public static boolean checkToken(String jwt)
    {
        try
        {
            if (jwt != null && validateJwtToken(jwt))
            {
                String username = getUserNameFromJwtToken(jwt);
                if(!username.isEmpty())
                    return true;
                else
                    return false;
            }
            else
                return false;

        } catch (Exception e) {
            return false;
        }
    }

    public  static String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(getjwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public  static boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(getjwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            return false;
        }
    }
}
