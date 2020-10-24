package br.com.beertech.fusion.util;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.UUID;

public class Support {

    public static String getjwtSecret() {
        return "grupofusion";
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

    public static boolean isCNPJ(String CNPJ)
    {
        // CNPJs sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") ||
                CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333") ||
                CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555") ||
                CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") ||
                CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999") ||
                (CNPJ.length() != 14))

            return(false);

        char dig13, dig14;
        int sm, i, r, num, peso;


        try {
            // Calculo do primeiro Digito Verificador
            sm = 0;
            peso = 2;
            for (i=11; i>=0; i--)
            {
                num = (int)(CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char)((11-r) + 48);

            //Digito Verificador
            sm = 0;
            peso = 2;
            for (i=12; i>=0; i--) {
                num = (int)(CNPJ.charAt(i)- 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char)((11-r) + 48);

            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
                return(true);
            else
                return(false);

        } catch (InputMismatchException error) {
            return(false);
        }
    }

}
