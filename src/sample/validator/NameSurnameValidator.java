package sample.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameSurnameValidator {

    private Pattern pattern;
    private Matcher matcher;
    private static final String NAME_PATTERN = "[A-Za-z]{1,}";
    public static final NameSurnameValidator INSTANCE = new NameSurnameValidator();

    private NameSurnameValidator(){}

    @SuppressWarnings("Duplicates")
    public boolean isValid(String name){
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        if(!name.equals("")) {
            return matcher.matches();
        } else{
            return false;
        }
    }
}
