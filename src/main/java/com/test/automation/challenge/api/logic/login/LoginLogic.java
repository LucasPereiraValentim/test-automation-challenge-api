package com.test.automation.challenge.api.logic.login;

import com.test.automation.challenge.api.logic.user.UserLogic;
import com.test.automation.challenge.api.utils.Assert;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import com.test.automation.challenge.api.utils.YamlUtils;
import com.test.automation.challenge.api.utils.FileUtils;
import com.test.automation.challenge.api.utils.AllureRestAssuredFilter;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Slf4j
public class LoginLogic {

    private String email;

    private String password;

    private static String authorization;

    private Assert anAssert;

    private Response response;

    public LoginLogic() {
        this.anAssert = new Assert();
    }

    public static String getAuthorization() {
        return authorization;
    }

    private HashMap<String, Object> getJsonRequest(String scenario) {
        HashMap<String, Object> jsonRequest = FileUtils.readJsonAsMap("login/login.json");

        if (scenario.contains("usuário sem permissão de admin")) {
            email = UserLogic.email;
            jsonRequest.put("email", email);
        }

        if (scenario.contains("usuário inválido")) {
            this.email = "usuarioInvalido";
            jsonRequest.put("email", email);
        } else if (scenario.contains("senha inválida")) {
            this.password = "senhaInvalida";
            jsonRequest.put("password", password);
        } else if (scenario.contains("usuário vazio")) {
            this.email = "";
            jsonRequest.put("email", email);
        } else if (scenario.contains("senha vazia")) {
            this.password = "";
            jsonRequest.put("password", password);
        } else if (scenario.contains("usuário nulo")) {
            this.email = null;
            jsonRequest.put("email", email);
        } else if (scenario.contains("senha nula")) {
            this.password = null;
            jsonRequest.put("password", password);
        }

        return jsonRequest;
    }

    public LoginLogic performLogin(String scenario) {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("login");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .body(this.getJsonRequest(scenario))
                .post(url);


        if (response.statusCode() == 200) {
            authorization = response.jsonPath().getString("authorization");
            log.info("Login realizado com sucesso. Token gerado.");
            log.info("Requisição processada com sucesso.");
            return this;
        }

        log.info("Response: {}", response.body().asString());
        return this;
    }


    public LoginLogic assertLoginSuccess(int statusCode) {
        this.anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponseNotNull(this.response, "authorization");
        return this;
    }

    public LoginLogic asseertLoginInvalid(int statusCode, String message, String jsonExpression) {
        this.anAssert
                .assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(message, this.response, jsonExpression);

        return this;
    }
}
