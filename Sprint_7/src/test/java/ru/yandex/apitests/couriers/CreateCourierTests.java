package ru.yandex.apitests.couriers;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.objects.methods.CouriersMethods;

public class CreateCourierTests extends CouriersMethods {

    private String login;
    private String password;
    private String firstName;

    public CreateCourierTests() {
    }

    @Before
    @Step("Preparation of test data")
    public void registrationData() {
        this.login = "Neeqoo";
        this.password = "12345";
        this.firstName = "Nick";
    }


    @After
    @Step("Clearing data in databases after the test")
    public void cleanAfterTests() {
        if (!isCourierCreated()) return;

        Integer idCourier = getIdCourier(loginCourier(login, password));

        if (idCourier != null) {
            deleteCourier(idCourier);
        }

        setIsCreated(false);
    }

    //   --- Курьера можно создать ---
    @Test
    @DisplayName("Creating a new courier")
    @Description("Testing the API for creating a new courier. The expected result: a new courier has been created")
    public void createNewCourierIsSuccess() {
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }

    //   --- Нельзя создать двух курьеров с одинаковыми логинами ---
    @Test
    @DisplayName("Creating couriers with the same data")
    @Description("A test of the possibility of creating two couriers with the same data. The expected result is that you cannot create identical couriers.")
    public void createSameCouriersIsFailed() {
        //Создание первого курьера
        Response response = createCourier(login, password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);

        //Создание второго курьера
        response = createCourier(login, password, firstName);

        checkStatusCode(response, 409);
        checkMessage(response, "message", "Этот логин уже используется. Попробуйте другой."); // ответ не из документации
    }

    //   --- Чтобы создать курьера, нужно передать в ручку все обязательные поля ---

    //Курьер не создается без логина
    @Test
    @DisplayName("Creating a courier without a login")
    @Description("Testing the API for creating a courier without a username. The expected result is that you can't create a courier without a username.")
    public void createCourierWithoutLoginIsFailed() {
        Response response = createCourier("", password, firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    //Курьер не создается без пароля
    @Test
    @DisplayName("Creating a courier without a password")
    @Description("Testing the API for creating a courier without a password. The expected result is that you cannot create a courier without a password.")
    public void createCourierWithoutPasswordIsFailed() {
        Response response = createCourier(login, "", firstName);
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    //Курьер не создается с пустыми полями
    @Test
    @DisplayName("Creating a courier without data (login, password, firstname)")
    @Description("Testing the API for creating a courier without data. The expected result is that you can't create a courier without data.")
    public void createCourierWithoutAllIsFailed() {
        Response response = createCourier("", "", "");
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 400);
        checkMessage(response, "message", "Недостаточно данных для создания учетной записи");
    }

    //Курьер создается успешно, если не передать имя курьера
    @Test
    @DisplayName("Creating a courier without a firstname")
    @Description("Testing the API for creating a courier without a firstname. The expected result: a new courier has been created.")
    public void createCourierWithoutFirstnameIsSuccess() {
        Response response = createCourier(login, password, "");
        setIsCreated(isCourierCreated(response, 201));

        checkStatusCode(response, 201);
        checkMessage(response, "ok", true);
    }

}
