package br.com.beertech.fusion.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

    // FIXME Verificar onde chama porque o SimpleDateFormat não é thread-safe
    // https://www.callicoder.com/java-simpledateformat-thread-safety-issues/
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

}
