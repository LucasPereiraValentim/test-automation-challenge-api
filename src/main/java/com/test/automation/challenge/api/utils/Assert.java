package com.test.automation.challenge.api.utils;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.qameta.allure.Allure.step;

public class Assert {

    public Assert assertStatusCode(int statusCode, Response response) {
        Assertions.assertEquals(statusCode, response.statusCode());
        step("Status code esperado: \"" + statusCode + "\" | Status code recebido: \"" + response.statusCode() + "\"", () -> {
            if (response.statusCode() != statusCode) {
                throw new AssertionError(
                        "Esperado: " + statusCode + ", mas veio: " + response.statusCode()
                );
            }
        });
        return this;
    }

    public Assert assertAtributeResponse(String valueExpected, Response response, String jsonExpression) {
        Assertions.assertEquals(valueExpected, response.jsonPath().getString(jsonExpression));
        step("Atributo \"" + jsonExpression + "\" no response esperado: " + valueExpected + " | Atributo \"" + jsonExpression + "\" no response recebido: " + response.jsonPath().getString(jsonExpression), () -> {
            if (!valueExpected.equals(response.jsonPath().getString(jsonExpression))) {
                throw new AssertionError(
                        "Esperado: " + valueExpected + ", mas veio: " + response.jsonPath().getString(jsonExpression)
                );
            }
        });
        return this;
    }

    public Assert assertAtributeResponseNotNull(Response response, String jsonExpression) {
        Assertions.assertNotNull(response.jsonPath().getString(jsonExpression));
        step("Atributo \"" + jsonExpression + "\" no response não é nulo: " + response.jsonPath().getString(jsonExpression), () -> {
            if (response.jsonPath().getString(jsonExpression) == null) {
                throw new AssertionError(
                        "JsonExpression verificado é nulo " + response.jsonPath().getString(jsonExpression)
                );
            }
        });
        return this;
    }


}
