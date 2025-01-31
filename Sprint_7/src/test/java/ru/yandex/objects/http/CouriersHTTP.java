package ru.yandex.objects.http;

import io.restassured.response.Response;
import ru.yandex.objects.communication.CourierRequest;
import ru.yandex.objects.resource.Api;
import ru.yandex.objects.resource.Urls;

public class CouriersHTTP extends BaseHTTP {

    public Response createCourier(CourierRequest courier) {
        return doPostRequest(
                Urls.SERVER_NAME + Api.CREATE_COURIER,
                courier,
                "application/json"
        );
    }

    public Response loginCourier(CourierRequest courier) {
        return doPostRequest(
                Urls.SERVER_NAME + Api.LOGIN_COURIER,
                courier,
                "application/json"
        );

    }

    public Response deleteCourier(Integer idCourier) {
        return doDeleteRequest(Urls.SERVER_NAME + Api.DELETE_COURIER + idCourier);
    }

}
