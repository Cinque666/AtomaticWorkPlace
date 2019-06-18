package sample.validator;

import java.util.regex.Pattern;

public class LoginValidator {
    private static final String SYMBOLS = "/-,.]}%#@!([])])";
//    private static final String REGEX = "/^[a-zA-Z](.[a-zA-Z0-9_-]*)$/";
    private static Pattern pattern;
    public static final LoginValidator INSTANCE = new LoginValidator();

    private LoginValidator(){}

    public boolean isValid(String login){
//        return !login.matches(SYMBOLS) && !login.isEmpty();
//                Pattern.compile(REGEX);
//        Matcher matcher = pattern.matcher(login);
//        if(Pattern.matcher(login));
        return !isEmptyLogin(login) && !login.contains(SYMBOLS);
    }
     private boolean isEmptyLogin(String login){
//        login.
//        return login.matches(VALID);
         return  login.contains(" ") || login.contains("_") || login.equals("")
                 || login.contains("-") || login.contains("+");
     }
}
