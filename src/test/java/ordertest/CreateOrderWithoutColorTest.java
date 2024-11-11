package ordertest;

import client.Client;
import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.Optional.empty;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.not;

public class CreateOrderWithoutColorTest extends Client {

    private OrderClient orderClient;

    private static final String FIRST_NAME = "Райан";
    private static final String LAST_NAME = "Гослинг";
    private static final String ADDRESS = "г. Лос-Анджелес, пр-т Ленина";
    private static final String METRO_STATION = "Арбатская";
    private static final String PHONE = "+79874563210";
    private static final int RENT_TIME = 5;
    private static final String DELIVERY_DATE = "2024-12-31";
    private static final String COMMENT = "Ты должен быть готов к тому, чтобы потерять всё";

    String track;

    @Before
    public void setUp() {
        RestAssured.requestSpecification = requestSpec;
    }

    @Test
    @DisplayName("Создание заказа без указания параметра color")
    @Description("Заказ можно создать, если не указать параметр color")
    public void createOrderWithoutColor() {
        OrderRequest order = new OrderRequest(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT);
        Response response = orderClient.createOrder(order);
        track = response
                .then()
                .extract()
                .path("track").toString();

        response
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .assertThat()
                .body("track", not(empty()));
    }

    @After
    public void cancelOrder() {
        OrderClient.cancel(track);
    }
}
