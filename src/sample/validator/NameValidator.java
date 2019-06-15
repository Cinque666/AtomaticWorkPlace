package sample.validator;

public class NameValidator {

    public static final NameValidator INSTANCE = new NameValidator();

    private NameValidator(){}

    public boolean isValid(String name){
        return !isEmptyName(name);
    }

    private boolean isEmptyName(String name){
        return name.equals("") || name.contains(" ");
    }
}
