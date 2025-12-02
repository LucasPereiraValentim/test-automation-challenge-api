package com.test.automation.challenge.api.logic.product;

import com.github.javafaker.Faker;
import com.test.automation.challenge.api.logic.login.LoginLogic;
import com.test.automation.challenge.api.utils.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

@Slf4j
public class ProductLogic {

    private Response response;

    private Assert anAssert;

    private JsonPath jsonPath;

    private String idProduct;

    private String nameProduct;

    private Integer price;

    private String descriptionProduct;

    private Integer quantity;

    public ProductLogic() {
        this.anAssert = new Assert();
    }

    public ProductLogic performRegisterProduct(String scenario) {
        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("product.registerProduct");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .headers(this.getHeaders(scenario))
                .filter(new AllureRestAssuredFilter())
                .body(this.getJsonRequest(scenario))
                .post(url);

        log.info("Response: {}", response.body().asString());

        this.idProduct = this.response.jsonPath().getString("_id");

        return this;
    }

    public ProductLogic getListProducts() {

        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("product.listProducts");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .get(url);

        log.info("Response: {}", response.body().asString());
        return this;
    }

    public ProductLogic getProductById() {

        this.jsonPath = this.response.jsonPath();

        String url = YamlUtils.getValueFromYaml("basePath") + YamlUtils.getValueFromYaml("product.findProdutctById") + jsonPath.getString("produtos[0]._id");

        log.info("Request enviada: {}", url);

        this.response = given()
                .contentType(ContentType.JSON)
                .filter(new AllureRestAssuredFilter())
                .get(url);

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
        jsonRequest.put("nome", this.getNameProduct());
        jsonRequest.put("preco", this.getPriceProduct());
        jsonRequest.put("descricao", this.getDescriptionProduct());
        jsonRequest.put("quantidade", this.getQuantity());
        return jsonRequest;
    }

    private Integer getQuantity() {
        if (quantity == null) {
            quantity = new Random().nextInt(700);
        }

        return quantity;
    }

    private String getDescriptionProduct() {
        if (descriptionProduct == null) {
            descriptionProduct = new Faker().lorem().sentence(12);
        }
        return descriptionProduct;
    }

    private Integer getPriceProduct() {
        if (price == null) {
            this.price = new Random().nextInt(5000);
        }
        return price;
    }

    private String getNameProduct() {
        if (nameProduct == null) {
            nameProduct = new Faker().commerce().productName();
        }
        return nameProduct;
    }

    public ProductLogic assertRegisterProductSuccess(int statusCode, String valueExpected, String jsonExpression, String jsonExpressionNotNull) {
        this.anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(valueExpected, this.response, jsonExpression)
                .assertAtributeResponseNotNull(this.response, jsonExpressionNotNull);
        return this;
    }

    public ProductLogic assertListProductAfter(int statusCode) {

        int index = APIUtils.getIndexByIdProduct(this.response, this.idProduct, "produtos");

        this.anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(this.getNameProduct(), this.response, "produtos[" + index + "].nome")
                .assertAtributeResponse(String.valueOf(this.getPriceProduct()), this.response, "produtos[" + index + "].preco")
                .assertAtributeResponse(this.descriptionProduct, this.response, "produtos[" + index + "].descricao")
                .assertAtributeResponse(String.valueOf(this.quantity), this.response, "produtos[" + index + "].quantidade")
                .assertAtributeResponse(this.idProduct, this.response, "produtos[" + index + "]._id");

        return this;
    }



    public ProductLogic assertFindByIdProduct(int statusCode) {
        this.anAssert.assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(jsonPath.getString("produtos[0].nome"), this.response, "nome")
                .assertAtributeResponse(jsonPath.getString("produtos[0].preco"), this.response, "preco")
                .assertAtributeResponse(jsonPath.getString("produtos[0].descricao"), this.response, "descricao")
                .assertAtributeResponse(jsonPath.getString("produtos[0].quantidade"), this.response, "quantidade")
                .assertAtributeResponse(jsonPath.getString("produtos[0]._id"), this.response, "_id");

        return this;
    }

    public ProductLogic asseertRegisterProductInvalid(int statusCode, String message, String jsonExpression) {
        this.anAssert
                .assertStatusCode(statusCode, this.response)
                .assertAtributeResponse(message, this.response, jsonExpression);

        return this;
    }
}
