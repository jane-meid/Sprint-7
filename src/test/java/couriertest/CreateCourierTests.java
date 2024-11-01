package couriertest;

import client.Client;
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


public class CreateCourierTests extends Client {
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.requestSpecification = requestSpec;
    }
    
    @Test
    @DisplayName("Создание курьера")
    @Description("Успешное создание курьера при вводе валидных данных")
    public void createCourier() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();
        
       Response response = courierClient.create(courier);

        response
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера при вводе валидных пароля и имени")

    public void createCourierWithoutLogin() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.createWithoutLogin(courier);

        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера при вводе валидных логина и имени")
    public void createCourierWithoutPassword() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.createWithoutPassword(courier);

        response.then().assertThat().statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    @Description("Создание курьера с валидными данными и повторное создание этого же курьера")
    public void createDoubleCouriers() {

        Courier courier = randomCourier();
        courierClient = new CourierClient();

        courierClient.create(courier);
        Response response = courierClient.create(courier);

        Response loginResponse = courierClient.login(courier);
        id = loginResponse.as(CourierId.class).getId();

        response
                .then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void tearDown() {
        if (id != 0) {
            courierClient.delete(id);
        }
    }

}
