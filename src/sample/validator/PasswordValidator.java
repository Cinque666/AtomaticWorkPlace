package sample.validator;

public class PasswordValidator {
    public static final PasswordValidator INSTANCE = new PasswordValidator();

    private PasswordValidator(){}

    public boolean isValid(String password){
        return !isEmptyPassword(password);
    }

    private boolean isEmptyPassword(String password){
        return password.equals("") || password.contains(" ");
    }
}
