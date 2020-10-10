package br.com.beertech.fusion.service;

import br.com.beertech.fusion.domain.Operacao;

public class Validator {

    Operacao ObjectValidator;

    public Validator(Operacao ObjectValidatorParameter)
    {
        ObjectValidator = ObjectValidatorParameter;
    }

    public boolean ValidateResponseRMQ()
    {
        boolean passed = false;

        if(ObjectValidator.getTipoOperacao().equals("DEPOSITO") || ObjectValidator.getTipoOperacao().equals("SAQUE"))
        {
            if(ObjectValidator.getValorOperacao() > 0)
                passed = true;
        }

        return passed;
    }
}
