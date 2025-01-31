package ru.yandex.apitests.orders;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.objects.methods.OrdersMethods;

public class GetOrdersListTests extends OrdersMethods {

    //Провека, что в тело ответа возвращается список заказов
    @Test
    @DisplayName("Get Order List")
    @Description("API test for getting a list of orders. The expected result - the list of orders is returned.")
    public void getOrderListIsSuccess() {
        Response response = getOrdersList();

        checkStatusCode(response, 200);
        checkOrdersInResponse(response);
    }

}
