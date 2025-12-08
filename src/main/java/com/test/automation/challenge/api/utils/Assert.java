package com.test.automation.challenge.api.utils;

import io.qameta.allure.Allure;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;

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

    public Assert assertJsonArrayIsEmpty(Response response, String jsonExpression) {
        List<?> array = response.jsonPath().getList(jsonExpression);

        Assertions.assertNotNull(array, "O atributo '" + jsonExpression + "' não pode ser nulo");
        Assertions.assertTrue(array.isEmpty(), "O array '" + jsonExpression + "' deve estar vazio");

        step("Array '" + jsonExpression + "' está vazio", () -> {
            if (!array.isEmpty()) {
                throw new AssertionError("Array '" + jsonExpression + "' esperado vazio, encontrado tamanho: " + array.size());
            }
        });

        return this;
    }

    public Assert assertJsonSchema(String json, String schemaPath) {
        anexarSchemaNoAllure(schemaPath);
        anexarJsonNoAllure(json);

        assertThat(json,
                JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath)
        );
        return this;
    }

    private void anexarSchemaNoAllure(String schemaPath) {
        try {
            String schema = new String(
                    Objects.requireNonNull(
                            APIUtils.class.getClassLoader().getResourceAsStream(schemaPath)
                    ).readAllBytes(),
                    StandardCharsets.UTF_8
            );

            Allure.addAttachment("Schema Validado: " + schemaPath,
                    "application/json", schema, "json");

        } catch (Exception ignored) {}
    }

    private static void anexarJsonNoAllure(String json) {
        Allure.addAttachment("JSON Validado", "application/json", json, "json");
    }

}
