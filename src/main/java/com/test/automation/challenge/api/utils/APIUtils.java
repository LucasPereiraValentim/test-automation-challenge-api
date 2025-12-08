package com.test.automation.challenge.api.utils;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;


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

    public static int getIndexById(Response response, String id, String jsonExpresionList) {
        List<Map<String, Object>> playload = response.jsonPath().getList(jsonExpresionList);

        for (int i = 0; i < playload.size(); i++) {
            Object idProduct = playload.get(i).get("_id");
            if (idProduct != null && idProduct.toString().trim().equalsIgnoreCase(id.trim())){
                log.info("ID encontrado no indice " + i + " | ID do produto: " + idProduct);
                return i;
            }
        }

        throw new RuntimeException("ID não encontrado no playload... | ID do prooduto: " + id);
    }
}
