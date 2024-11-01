package ordertest;
import client.Client;
import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.OrderRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest extends Client {

    private static final String FIRST_NAME = "Райан";
    private static final String LAST_NAME = "Гослинг";
    private static final String ADDRESS = "г. Лос-Анджелес, пр-т Ленина";
    private static final String METRO_STATION = "Арбатская";
    private static final String PHONE = "+79874563210";
    private static final int RENT_TIME = 5;
    private static final String DELIVERY_DATE = "2024-12-31";
    private static final String COMMENT = "Ты должен быть готов к тому, чтобы потерять всё";

    private final String[] colors;
    String track;

    private OrderClient orderClient;

    public CreateOrderParameterizedTest(String[] colors) {
        this.colors = colors;
    }

    @Before
    public void setUp() {
        RestAssured.requestSpecification = requestSpec;
    }

    @Parameterized.Parameters(name = "colour = ''{0}''")
    public static Object[][] orderCreationParameters() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Заказ можно создать с указанием только одного цвета или обоих цветов")
    public void createOrder() {
        OrderRequest order = new OrderRequest(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT, colors);
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
                .body("track", notNullValue());
    }

    @After
    public void cancelOrder() {
        OrderClient.cancel(track);
    }
}