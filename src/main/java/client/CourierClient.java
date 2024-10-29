package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;

import static io.restassured.RestAssured.given;
import static models.CourierCreds.*;
import static models.CourierWithoutLogin.passwordFromCourier;
import static models.CourierWithoutPassword.loginFromCourier;

public class CourierClient {

    @Step("Создание курьера")
    public static Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("api/v1/courier");
    }

    @Step("Авторизация курьера")
    public static Response login(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(credsFromCourier(courier))
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Создание курьера без логина")
    public static Response createWithoutLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(passwordFromCourier(courier))
                .when()
                .post("api/v1/courier");
    }

    @Step("Создание курьера без пароля")
    public static Response createWithoutPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginFromCourier(courier))
                .when()
                .post("api/v1/courier");
    }

    @Step("Авторизация курьера без логина")
    public static Response loginWithoutLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(passwordFromCourier(courier))
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Авторизация курьера без пароля")
    public static Response loginWithoutPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginFromCourier(courier))
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Авторизация курьера с несуществующим логином")
    public static Response loginWithNonExistentLogin(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(nonExistentLoginFromCourier(courier))
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Авторизация курьера с несуществующим паролем")
    public static Response loginWithNonExistentPassword(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(nonExistentPasswordFromCourier(courier))
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public static Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete("api/v1/courier/" + id);
    }

}
