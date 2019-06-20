package sample.bean;

public class EventView {

    private int number;
    private String workerSurname;
    private String customerSurname;
    private int cost;

    public EventView() {
    }

    public EventView(int number, String workerSurname, String customerSurname, int cost) {
        this.number = number;
        this.workerSurname = workerSurname;
        this.customerSurname = customerSurname;
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getWorkerSurname() {
        return workerSurname;
    }

    public void setWorkerSurname(String workerSurname) {
        this.workerSurname = workerSurname;
    }

    public String getCustomerSurname() {
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
