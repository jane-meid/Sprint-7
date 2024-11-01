package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String BASE_URL = "/api/v1/orders";
    private static final String GET_URL = "/v1/orders";
    private static final String CANCEL_URL = "/api/v1/orders/cancel?track=";

    @Step("Создание заказа")
    public static Response createOrder(OrderRequest orderRequest) {

        return given()
                .header("Content-Type", "application/json")
                .body(orderRequest)
                .when()
                .post(BASE_URL);
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {

        return given()
                .header("Content-Type", "application/json")
                .when()
                .get(GET_URL);
    }

    @Step("Отмена заказа")
    public static Response cancel(String track) {

    return given()
            .header("Content-type", "application/json")
            .when()
            .put(CANCEL_URL + track);
}
}
