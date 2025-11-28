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

        Allure.addAttachment("Request para URL: " + requestSpec.getURI(),
                requestSpec.getMethod() + " " + requestSpec.getURI() +
                        "\nHeaders: " + requestSpec.getHeaders() +
                        "\nBody: " + (requestSpec.getBody() != null ? requestSpec.getBody() : "N/A"));

        Allure.addAttachment("Response",
                "Status: " + response.getStatusCode() +
                        "\nBody: " + response.getBody().asPrettyString());

        return response;
    }
}
