package sample.bean;

public class Event {

    public Event(){}

    public Event(int idEvent, int idCustomer, int idUser, int cost){
        this.idUser = idUser;
        this.idCustomer = idCustomer;
        this.idEvent = idEvent;
        this.cost = cost;
    }

    private int idEvent;
    private int idCustomer;
    private int idUser;
    private int cost;

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
