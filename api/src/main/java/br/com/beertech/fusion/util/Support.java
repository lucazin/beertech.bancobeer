package br.com.beertech.fusion.util;

import br.com.beertech.fusion.service.security.jwt.JwtUtils;
import br.com.beertech.fusion.service.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Configuration
public class Support {

    @Autowired
    private static UserDetailsServiceImpl userDetailsService;

    public static String getjwtSecret() {
        return "grupofusion";
    }

    public static String getDataAtual() {
        SimpleDateFormat HoraFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date now = new Date();
        return HoraFormat.format(now);
    }
    public static String getIdentifyDate() {
        SimpleDateFormat HoraFormat = new SimpleDateFormat("ddMMyyyyHHmmss.sss");
        Date now = new Date();
        return HoraFormat.format(now);
    }

    public static String getRandomNumberAccountAgency() {
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        return String.format("%04d", number);
    }
    public static String getRandomNumberAccountNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
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
