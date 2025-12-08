package com.test.automation.challenge.api.logic.user;

import com.github.javafaker.Faker;
import com.test.automation.challenge.api.enums.StatusCode;
import com.test.automation.challenge.api.utils.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

@Slf4j
public class UserLogic {

    private Response response;

    public static String email;

    private String id;

    private Assert anAssert;

   public UserLogic() {
       this.anAssert = new Assert();
   }

    public UserLogic deleteUserById() {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("users.userById") + this.id;

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .delete(url);

        log.info("Response: {}", response.body().asString());

        return this;
    }

    public UserLogic updateUserById() {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("users.userById") + this.id;

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .body(this.getJsonRequestUpdateUser())
                .put(url);

        log.info("Response: {}", response.body().asString());

        return this;
    }

    public UserLogic performCreateUser(String scenario) {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("users.createdAndListUser");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .body(this.getJsonRequestCreateUser(scenario))
                .post(url);

        log.info("Response: {}", response.body().asString());

        this.id = this.response.jsonPath().getString("_id");

        return this;
    }

    public UserLogic getUserById() {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("users.userById") + this.id;

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .get(url);

        log.info("Response: {}", response.body().asString());

        return this;
    }

    private HashMap<String, Object> getJsonRequestUpdateUser() {
        HashMap<String, Object> jsonRequest = FileUtils.readJsonAsMap("user/update_user.json");

        email = new Faker().internet().emailAddress();

        jsonRequest.put("email", email);

        return jsonRequest;
    }

    private HashMap<String, Object> getJsonRequestCreateUser(String scenario) {
        HashMap<String, Object> jsonRequest = FileUtils.readJsonAsMap("user/create_user.json");

        email = new Faker().internet().emailAddress();

        jsonRequest.put("email", email);

        if (scenario.contains("sem permissão de admin")) {
            jsonRequest.put("administrador", "false");
        }

        log.info("Usuário criado com o e-mail: {} | Permissão do usuário criada -> admin: {}", email, jsonRequest.get("administrador").toString());

        return jsonRequest;
    }

    public UserLogic listUsers() {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("users.createdAndListUser");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .get(url);

        log.info("Response: {}", response.body().asString());

        return this;
    }

    public String getPathSchemaListUsers() {
        String pathSchema = "schemas/user/schema_list_users.json";
        log.info("Arquivo para validação do schema: {}", pathSchema);
        return pathSchema;
    }

    public String getPathSchemaReturnUserById() {
        String pathSchema = "schemas/user/schema_return_user_by_id.json";
        log.info("Arquivo para validação do schema: {}", pathSchema);
        return pathSchema;
    }

    public UserLogic assertListUsersSuccess(int statusCode, String jsonExpression) {
        anAssert.assertStatusCode(statusCode, this.response)
                .assertJsonSchema(this.response.body().asString(), this.getPathSchemaListUsers())
                .assertAtributeResponseNotNull(this.response, jsonExpression);
        return this;
    }

    public UserLogic assertReturnUserSuccess(int statusCode) {
        anAssert.assertStatusCode(statusCode, this.response)
                .assertJsonSchema(this.response.body().asString(), this.getPathSchemaReturnUserById())
                .assertAtributeResponse(this.id, this.response, "_id");
        return this;
    }

    public UserLogic assertRequestSuccess(int statusCode, String valueExpected, String jsonExpression) {
        anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(valueExpected, this.response, jsonExpression);
        return this;
    }

    public UserLogic assertAtributeListUsers(int statusCode) {
       int index = APIUtils.getIndexById(this.response, this.id, "usuarios");
        anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(email, this.response, "usuarios[" + index + "].email")
                .assertAtributeResponse(this.id, this.response, "usuarios[" + index + "]._id");
       return this;
    }

}
