package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;

import static io.restassured.RestAssured.given;
import static models.CourierCreds.*;
import static models.CourierWithoutLogin.passwordFromCourier;
import static models.CourierWithoutPassword.loginFromCourier;

public class CourierClient {

    private static final String BASE_URL = "api/v1/courier";
    private static final String LOGIN_URL = "api/v1/courier/login";

    @Step("Создание курьера")
    public static Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BASE_URL);
    }

    @Step("Авторизация курьера")
    public static Response login(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(credsFromCourier(courier))
                .when()
                .post(LOGIN_URL);
    }

    @Step("Создание курьера без логина")
    public static Response createWithoutLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(passwordFromCourier(courier))
                .when()
                .post(BASE_URL);
    }

    @Step("Создание курьера без пароля")
    public static Response createWithoutPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginFromCourier(courier))
                .when()
                .post(BASE_URL);
    }

    @Step("Авторизация курьера без логина")
    public static Response loginWithoutLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(passwordFromCourier(courier))
                .when()
                .post(LOGIN_URL);
    }

    @Step("Авторизация курьера без пароля")
    public static Response loginWithoutPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginFromCourier(courier))
                .when()
                .post(LOGIN_URL);
    }

    @Step("Авторизация курьера с несуществующим логином")
    public static Response loginWithNonExistentLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(nonExistentLoginFromCourier(courier))
                .when()
                .post(LOGIN_URL);
    }

    @Step("Авторизация курьера с несуществующим паролем")
    public static Response loginWithNonExistentPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(nonExistentPasswordFromCourier(courier))
                .when()
                .post(LOGIN_URL);
    }

    @Step("Удаление курьера")
    public static Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(BASE_URL + "/" + id);
    }

}
