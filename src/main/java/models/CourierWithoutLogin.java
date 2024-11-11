package models;

public class CourierWithoutLogin {
    private String password;
    private String login;

    public CourierWithoutLogin(String login, String password) {

        this.password = password;
        this.login = login;
    }

    public static CourierWithoutLogin passwordFromCourier(Courier courier) {
        return new CourierWithoutLogin(null, courier.getPassword());
    }
}
