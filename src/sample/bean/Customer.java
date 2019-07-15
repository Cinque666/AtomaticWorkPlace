package sample.bean;

public class Customer {

    private int idCustomers;
    private String name;
    private String surname;
    private String address;
    private String passport;

    public Customer(int idCustomers, String name, String surname, String address, String passport){
        this.idCustomers = idCustomers;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
    }

    public Customer(String name, String surname, String address, String passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;
    }

    public int getIdCustomers() {
        return idCustomers;
    }

    public void setIdCustomers(int idCustomers) {
        this.idCustomers = idCustomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
