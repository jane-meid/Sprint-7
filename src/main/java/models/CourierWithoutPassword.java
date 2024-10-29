package models;

public class CourierWithoutPassword {

    private String login;
    private String password;

    public CourierWithoutPassword(String login, String password) {

        this.login = login;
        this.password = password;
    }

    public static CourierWithoutPassword loginFromCourier(Courier courier) {
        return new CourierWithoutPassword(courier.getLogin(), null);
    }

}
