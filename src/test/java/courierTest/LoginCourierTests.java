package courierTest;

import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import models.CourierId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generators.CourierGenerator.randomCourier;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTests {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Успешный логин курьера")
    @Description("Логин курьера в системе. Курьер может авторизоваться. Успешный запрос возвращает id.")
    public void loginCourier() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);

        Response response = courierClient.login(courier);
        id = response.as(CourierId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. Передаётся пустой логин")
    public void loginCourierWithoutLogin(){
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);
        Response response = courierClient.loginWithoutLogin(courier);

        response
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. Передаётся пустой пароль")
    public void loginCourierWithoutPassword() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);
        Response response = courierClient.loginWithoutPassword(courier);

        response
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Авторизация курьера при вводе несуществующего логина")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. Передаётся несуществующий логин")
    public void authorizationCourierWithNonExistentLogin() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);
        Response response = courierClient.loginWithNonExistentLogin(courier);

        response
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Авторизация курьера при вводе несуществующего пароля")
    @Description("Для авторизации курьера необходимо передать все обязательные поля. Передаётся несуществующий логин")
    public void authorizationCourierWithNonExistentPassword() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);
        Response response = courierClient.loginWithNonExistentPassword(courier);

        response
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }



    @After
    public void tearDown() {
        courierClient.delete(id);
    }

    }

