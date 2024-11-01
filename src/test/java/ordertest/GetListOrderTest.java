package ordertest;

import client.Client;
import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;


import static java.util.Optional.empty;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.not;

public class GetListOrderTest extends Client {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        RestAssured.requestSpecification = requestSpec;
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов.")
    public void getOrderList() {
        Response response = OrderClient.getOrderList();
        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .assertThat()
                .body("orders", not(empty()));

    }

}
