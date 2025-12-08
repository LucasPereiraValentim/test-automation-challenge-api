package com.test.automation.challenge.api.utils;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class AllureRestAssuredFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        Response response = ctx.next(requestSpec, responseSpec);

        String requestBody = requestSpec.getBody() != null
                ? requestSpec.getBody().toString()
                : "N/A";

        Allure.addAttachment(
                "Request",
                "Method: " + requestSpec.getMethod() +
                        "\nURL: " + requestSpec.getURI() +
                        "\nHeaders: " + requestSpec.getHeaders() +
                        "\nBody:\n" + requestBody
        );

        Allure.addAttachment(
                "Response (" + response.getStatusCode() + ")",
                "application/json",
                response.getBody().asPrettyString(),
                "json"
        );

        return response;
    }
}
