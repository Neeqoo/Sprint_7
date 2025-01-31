package ru.yandex.objects.http;

import io.restassured.response.Response;
import ru.yandex.objects.communication.OrderRequest;
import ru.yandex.objects.resource.Api;
import ru.yandex.objects.resource.Urls;

import java.util.HashMap;
import java.util.Map;

public class OrdersHTTP extends BaseHTTP {

    public Response createOrder(OrderRequest order) {
        return doPostRequest(
                Urls.SERVER_NAME + Api.CREATE_ORDER,
                order,
                "application/json"
        );
    }

    public Response deleteOrder(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return doPutRequest(
                Urls.SERVER_NAME + Api.DELETE_ORDER,
                queryParams
        );
    }

    public Response getOrdersList() {
        return doGetRequest(Urls.SERVER_NAME + Api.GET_ORDERS_LIST);
    }

}
