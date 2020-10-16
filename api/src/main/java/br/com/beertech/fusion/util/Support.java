package br.com.beertech.fusion.util;

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
}
