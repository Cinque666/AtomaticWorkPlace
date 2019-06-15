package sample.validator;

public class SurnameValidator {

    public static final SurnameValidator INSTANCE = new SurnameValidator();

    private SurnameValidator(){}

    public boolean isValid(String name){
        return !isEmptySurname(name);
    }

    private boolean isEmptySurname(String name){
        return name.equals("") || name.contains(" ");
    }
}
