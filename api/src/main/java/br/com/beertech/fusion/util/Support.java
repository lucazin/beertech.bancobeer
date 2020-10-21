package br.com.beertech.fusion.util;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Support {

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

    public static String getjwtSecret() {
        return "grupofusion";
    }

    public static String checkToken(String jwt)
    {
        String tokenProvided= jwt.replace("Bearer ","");
        if (jwt != null && validateToken(tokenProvided))
        {
            String username = getusernameToken(tokenProvided);
            if(!username.isEmpty())
                return username;
            else
                return "NOTFOUND";
        }
        else
            return "NOTFOUND";
    }

    public  static String getusernameToken(String token) {
        return Jwts.parser().setSigningKey(getjwtSecret()).parseClaimsJws(token).getBody().getSubject();
    }

    public  static boolean validateToken(String authToken) {
        try
        {
            Jwts.parser().setSigningKey(getjwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            return false;
        }
    }
}
