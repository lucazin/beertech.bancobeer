package br.com.beertech.fusion.util;

public class SwaggerDoc {

    public static final String getaccountsTitle = "List all bank accounts.";
    public static final String getaccountsNotes = "List all bank accounts for all customers registered in application. No parameters are needed.";

    public static final String getaccountHashTitle = "Recovers customer's bank account by hash values.";
    public static final String getaccountHashNotes = "Recovers customer's bank account by hashing. Passing only the hash parameter in the request body." +
            " The parameter to be sent in the request is just 'hashAccount'";

    public static final String getaccountIdTitle = "Recovers customer's bank account by id value.";
    public static final String getaccountIdNotes = "Recovers customer's bank account by id value. " +
            "Passing only the id parameter in the request body. The parameter to be sent in the request is just 'idAccount'.";


    public static final String signupTitle = "Register customer at beercoin bank and create the integrated account, savings and checking account.";
    public static final String signupNotes = "In this call, you must pass a payload in the json format in the request body." +
            " The type of integrated account is 1 and the permission must pass in an array, either type moderator or user.";

    public static final String signinTitle = "Performs user authentication in JWT format.";
    public static final String signinNotes = "It authenticates the client using JWT, passing the client username and password." +
            " After authentication, the access token and permission of this user will be returned, along with some information.";


    public static final String transactionTitle = "Lists all bank transactions.";
    public static final String transactionNotes = "Lists all bank transactions for all users. No parameters are required when requesting the operation.";


    public static final String transactionHashTitle = "Lists all bank transactions for a specific customer. ";
    public static final String transactionHashNotes = "Lists all bank transactions for a specific customer. by the bank account hash.";


    public static final String transactionAccountNumberTitle = "Lists all bank transactions for a specific customer. ";
    public static final String transactionAccountNumberNotes = "Lists all bank transactions for a specific customer. by the agency and bank account number.";


    public static final String totalbalanceTitle = "Show balance beerbank.";
    public static final String totalbalanceNotes = "Shows the balance summing up all bank accounts.";

    public static final String accountbalanceHashTitle = "Show balance account beerbank.";
    public static final String accountbalanceHashNotes = "Shows the balance of the customer's bank account, using the hash parameter in the path variable.";

    public static final String accountbalanceAgencyAccountTitle = "Show balance account beerbank.";
    public static final String accountbalanceAgencyAccountNotes = "Shows the balance of the customer's bank account, using the agency and account number parameter in the path variable.";

    public static final String bankstatementTitle = "Show bank statement information";
    public static final String bankstatementNotes = "It shows the bank account statement of the customer informing as a parameter" +
            " in the request body the hash of the customer account and the type of operation to perform the filter." +
            " If the type of transaction is not entered in the request body, all transactions will be returned.";


    public static final String transfersQueueTitle = "Transfers from one bank account to another bank account.";
    public static final String transfersQueueNotes = "In this transfer operation, the parameters identifying the origin of the bank account," +
            " the destination of the bank account, the amount to be transferred and the token received and validated in the authentication as the" +
            " last parameter of this operation must be informed in the request body. Just moderator role can access this operation.";

    public static final String operationQueueTitle = "Performs the deposit and withdrawal operation.";
    public static final String operationQueueNotes = "The main function of this call is to carry out bank deposit and withdrawal operations.\n" +
            "It is necessary to pass the payload json in the request.\n" +
            "It is important to note that the typeOperation attribute for deposit must be BANKDEPOSIT and for WITHDRAW it must be WITHDRAW.\n" +
            "The other parameters sent will be: The value of the transaction, the bank account hash," +
            " the branch and account number and finally the customer's token.";
}
