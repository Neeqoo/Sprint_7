package ru.yandex.apitests.orders;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.objects.methods.OrdersMethods;

import java.util.List;

@RunWith(Parameterized.class)

public class CreateOrderTests extends OrdersMethods {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> scooterColor;
    private Integer trackId;

    public CreateOrderTests(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()}, // не выбран
                {List.of("BLACK")}, // выбран черный
                {List.of("GREY")}, // выбран серый
                {List.of("BLACK", "GREY")}, // выбраны оба
        };
    }

    @Before
    @Step("Preparation of test data")
    public void prepareTestData() {
        this.firstName = "Николай";
        this.lastName = "Николаевич";
        this.address = "Москва, Русаковская, д.27";
        this.phone = "+7(999)123-45-67";
        this.rentTime = "1";
        this.deliveryDate = "2025-01-15";
        this.comment = "На связи с 12:00 до 21:00";
    }

    @After
    @Step("Clearing data in databases after the test")
    public void clearAfterTests() {
        if (trackId == null) return;

        deleteOrder(trackId);
    }

    //Проверка заказов с выбором одного или двух цветов. Либо без выбора цвета (оставить поле пустым).
    @Test
    @DisplayName("Create order")
    @Description("Order creation API test. The expected result - the order is created, the track is returned")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", scooterColor);

        Response response = createOrder(firstName, lastName, address, phone, rentTime, deliveryDate, comment, scooterColor);
        checkStatusCode(response, 201);
        checkResponseParamNotNull(response, "track");

        this.trackId = getTrack(response);
    }

}
