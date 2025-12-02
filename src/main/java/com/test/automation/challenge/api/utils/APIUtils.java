package com.test.automation.challenge.api.utils;

import groovyjarjarantlr4.v4.parse.v3TreeGrammarException;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;


@Slf4j
public class APIUtils {

    public static String getDisplayName(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            DisplayName displayName = method.getAnnotation(DisplayName.class);
            return displayName != null ? displayName.value() : null;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Erro ao obter nome do cenário...");
        }
    }

    public static int getIndexByIdProduct(Response response, String findIdProduct, String jsonExpresionList) {
        List<Map<String, Object>> playload = response.jsonPath().getList(jsonExpresionList);

        for (int i = 0; i < playload.size(); i++) {
            Object idProduct = playload.get(i).get("_id");
            if (idProduct != null && idProduct.toString().trim().equalsIgnoreCase(findIdProduct.trim())){
                log.info("Produto encontrado pelo ID no indice " + i + " | ID do produto: " + idProduct);
                return i;
            }
        }

        throw new RuntimeException("ID de produto não encontrado no playload... | ID do prooduto: " + findIdProduct);
    }
}
