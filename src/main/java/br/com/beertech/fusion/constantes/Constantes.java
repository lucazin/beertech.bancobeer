package br.com.beertech.fusion.constantes;

import java.math.BigDecimal;

public interface Constantes {

    interface Numbers {
        Integer NUMBER_1 = 1;
        Integer NUMBER_2 = 2;
        Integer NUMBER_3 = 3;
    }

    interface Products {
        String ANTARTICA_SUZ_ZERO = "Antarctica Sub-Zero";
        String CARACU = "Caracu";
        String BRAHMA = "Brahma";
    }

    interface Prices {
        BigDecimal OITO_REAIS_E_CINQUENTA_CENTAVOS = new BigDecimal("8.50");
        BigDecimal NOVE_REAIS_E_CINQUENTA_CENTAVOS = new BigDecimal("9.50");
        BigDecimal CINCO_REAIS_E_CINQUENTA_CENTAVOS = new BigDecimal("5.50");
    }

}

