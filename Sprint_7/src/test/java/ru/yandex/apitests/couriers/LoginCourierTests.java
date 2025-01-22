package ru.yandex.apitests.couriers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.objects.methods.CouriersMethods;

public class LoginCourierTests extends CouriersMethods {

    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Preparation of test data")
    public void testCourier() {
        this.login = "Neeqoo";
        this.password = "12345";
        this.firstName = "Nick";

        createCourier(login, password, firstName);
    }

    @After
    @Step("Clearing data in databases after the test")
    public void clearAfterTests() {
        Integer idCourier = getIdCourier(loginCourier(login, password));
        if (idCourier == null) return;

        deleteCourier(idCourier);
    }

    //    --- Курьер может авторизоваться ---
    @Test
    @DisplayName("Courier's login to the system")
    @Description("A test of the courier's login API. The expected result is that the entrance has been completed, and the courier's ID is returned.")
    public void loginCourierIsSuccess() {
        Response response = loginCourier(login,password);

        checkStatusCode(response, 200);
        checkCourierIDNotNull(response);
    }

    //    --- Для авторизации нужно передать верные обязательные поля ---

    //Пропытка авторизации курьера без данных
    @Test
    @DisplayName("Courier's login to the system without data (login, password)")
    @Description("A test of the courier's login API without data (login, password). The expected result is that no entry has been made.")
    public void loginCourierWithoutAllIsFailed() {
        Response response = loginCourier("", "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    //Попытка авторизации курьера без логина
    @Test
    @DisplayName("The courier's login to the system without a login")
    @Description("A test of the courier's login API without a login. The expected result is that no entry has been made.")
    public void loginCourierWithoutLoginIsFailed() {
        Response response = loginCourier("", password);

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    //Попытка авторизации курьера без пароля
    @Test
    @DisplayName("The courier's login to the system without a password")
    @Description("A test of the courier's login API without a password. The expected result is that no entry has been made.")
    public void loginCourierMissingPasswordParamIsFailed() {
        Response response = loginCourier(login, "");

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для входа");
    }

    //Попытка авторизации курьера с неверным логином
    @Test
    @DisplayName("The courier's login to the system with an incorrect login")
    @Description("API login test for a courier with an invalid login. The expected result is that no entry has been made.")
    public void loginCourierIncorrectLoginIsFailed() {
        Response response = loginCourier("neqoneqo", password);

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }

    //Попытка авторизации курьера с неверным паролем
    @Test
    @DisplayName("The courier's login to the system with an incorrect password")
    @Description("A test of the courier's login API with an incorrect password. The expected result is that no entry has been made.")
    public void loginCourierIncorrectPasswordIsFailed() {
        Response response = loginCourier(login, "11223344");

        checkStatusCode(response, 404);
        checkMessage(response, "message", "Учетная запись не найдена");
    }

}
