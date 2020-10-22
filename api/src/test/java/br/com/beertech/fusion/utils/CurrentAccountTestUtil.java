package br.com.beertech.fusion.utils;

import br.com.beertech.fusion.domain.CurrentAccount;

public class CurrentAccountTestUtil {

    private static final Long ACCOUNT_ID = 1L;
    private static final String ACCOUNT_HASH = "hash";
    private static final String ACCOUNT_CNPJ = "12345678912";

    public static CurrentAccount currentAccountBuilder() {
        CurrentAccount account = new CurrentAccount();
        account.setIdConta(ACCOUNT_ID);
        account.setHash(ACCOUNT_HASH);
        account.setCnpj(ACCOUNT_CNPJ);

        return account;
    }
}
