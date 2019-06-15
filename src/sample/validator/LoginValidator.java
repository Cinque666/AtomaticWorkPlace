package sample.validator;

import javafx.scene.control.Alert;

public class LoginValidator {
    private static final String SYMBOLS = "/-,.{][}^%$#@!()_+=";
    public static final LoginValidator INSTANCE = new LoginValidator();

    private LoginValidator(){}

    public boolean isValid(String login){
        return !isEmptyLogin(login) && !login.contains(SYMBOLS);
    }
     private boolean isEmptyLogin(String login){
        return login.contains(" ") || login.contains("_") || login.equals("");
     }
}
