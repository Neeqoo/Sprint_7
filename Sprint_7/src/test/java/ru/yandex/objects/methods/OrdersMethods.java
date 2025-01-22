package ru.yandex.objects.methods;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.objects.communication.OrderRequest;
import ru.yandex.objects.communication.OrderResponse;
import ru.yandex.objects.http.OrdersHTTP;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrdersMethods {

    private final OrdersHTTP ordersHTTP = new OrdersHTTP();

    @Step("Запрос на создание заказа")
    public Response createOrder(String firstName, String lastName, String address, String phone,
                                String rentTime, String deliveryDate, String comment, List<String> scooterColor) {
        return ordersHTTP.createOrder(new OrderRequest(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, scooterColor));
    }

    @Step("Запрос на удаление заказа по ID")
    public Response deleteOrder(Integer trackId) {
        return ordersHTTP.deleteOrder(trackId);
    }

    @Step("Проверка кода ответа")
    public void checkStatusCode(Response response, int code) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(code);
    }

    @Step("Проверка возврата track-номера заказа")
    public void checkResponseParamNotNull(Response response, String label) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body(label, notNullValue());
    }

    @Step("Получение track-номера заказа")
    public Integer getTrack(Response response) {
        return response.body().as(OrderResponse.class).getTrack();
    }

    @Step("Запрос на получение списка заказов")
    public Response getOrdersList() {
        return ordersHTTP.getOrdersList();
    }

    @Step("Проверка наличия заказов в теле ответа")
    public void checkOrdersInResponse(Response response) {
        Allure.addAttachment("Список заказов", response.getBody().asInputStream());
        response.then().assertThat().body("orders", notNullValue());
    }

}
