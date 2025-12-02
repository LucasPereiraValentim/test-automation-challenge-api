package com.test.automation.challenge.api.logic.register;

import com.github.javafaker.Faker;
import com.test.automation.challenge.api.logic.login.LoginLogic;
import com.test.automation.challenge.api.utils.AllureRestAssuredFilter;
import com.test.automation.challenge.api.utils.Assert;
import com.test.automation.challenge.api.utils.FileUtils;
import com.test.automation.challenge.api.utils.YamlUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

@Slf4j
public class RegisterProductLogic {

    Response response;

    private Assert anAssert;

    public RegisterProductLogic() {
        this.anAssert = new Assert();
    }

    public RegisterProductLogic performRegisterProduct(String scenario) {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("product.registerProduct");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .headers(this.getHeaders(scenario))
                .filter(new AllureRestAssuredFilter())
                .body(this.getJsonRequest(scenario))
                .post(url);

        log.info("Response: {}", response.body().asString());
        return this;
    }

    private Map<String, String> getHeaders(String scenario) {
        Map<String, String> headers = new HashMap<>();
        if (scenario.contains("com token inválido")) {
            headers.put("Authorization", "Bearer testeAuthorizationInvalido");
            return headers;
        } else if (scenario.contains("token vazio")) {
            headers.put("Authorization", "");
            return headers;
        } else if (scenario.contains("token nulo")) {
            headers.put("Authorization", null);
            return headers;
        }

        headers.put("Authorization", LoginLogic.getAuthorization());
        return headers;
    }

    private HashMap<String, Object> getJsonRequest(String scenario) {
        HashMap<String, Object> jsonRequest = FileUtils.readJsonAsMap("products/register_product.json");
        if (!scenario.contains("sucesso")) {
            if (scenario.contains("caracteres alfanúmericos no campo de preço")) {
                jsonRequest.put("preco", "testeInvalido");
            } else if (scenario.contains("caracteres alfanúmericos no campo de quantidade")) {
                jsonRequest.put("quantidade", "testeInvalido");
            }
            return  jsonRequest;
        }
        jsonRequest.put("nome", new Faker().commerce().productName());
        jsonRequest.put("preco", new Random().nextInt(5000));
        jsonRequest.put("descricao", new Faker().lorem().sentence(12));
        jsonRequest.put("quantidade", new Random().nextInt(700));
        return jsonRequest;
    }

    public RegisterProductLogic assertRegisterProductSuccess(int statusCode, String valueExpected, String jsonExpression, String jsonExpressionNotNull) {
        this.anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(valueExpected, this.response, jsonExpression)
                .assertAtributeResponseNotNull(this.response, jsonExpressionNotNull);
        return this;
    }

    public RegisterProductLogic asseertRegisterProductInvalid(int statusCode, String message, String jsonExpression) {
        this.anAssert
                .assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(message, this.response, jsonExpression);

        return this;
    }
}
