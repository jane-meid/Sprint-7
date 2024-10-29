package models;

public class CourierCreds {

    private String login;
    private String password;

    public CourierCreds(String login, String password) {

        this.login = login;
        this.password = password;
    }

    public static CourierCreds credsFromCourier (Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

    public static CourierCreds nonExistentLoginFromCourier(Courier courier) {
        return new CourierCreds(courier.getLogin() + "jjj", courier.getPassword());
    }

    public static CourierCreds nonExistentPasswordFromCourier(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword() + "jjj");
    }

}
