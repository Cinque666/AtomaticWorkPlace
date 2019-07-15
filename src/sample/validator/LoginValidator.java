package sample.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String LOGIN_PATTERN = "[A-Za-z0-9]{1,}";
    public static final LoginValidator INSTANCE = new LoginValidator();

    private LoginValidator(){

    }

    @SuppressWarnings("Duplicates")
    public boolean isValid(String login){
        pattern = Pattern.compile(LOGIN_PATTERN);
        matcher = pattern.matcher(login);
        if(!login.equals("")) {
            return matcher.matches();
        } else {
            return false;
        }
    }
}
