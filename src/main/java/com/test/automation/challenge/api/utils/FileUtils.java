package com.test.automation.challenge.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;

@Slf4j
public class FileUtils {

    public static HashMap<String, Object> readJsonAsMap(String path) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream in = FileUtils.class.getClassLoader().getResourceAsStream("fixtures/" + path)) {
            if (in == null) {
                throw new RuntimeException("Arquivo JSON não encontrado: " + path);
            }
            HashMap<String, Object> result = new HashMap<>(mapper.readValue(in, HashMap.class));
            log.info("JSON lido com sucesso: {}", path);
            return result;
        } catch (Exception e) {
            log.error("Erro ao ler JSON: {}", path, e);
            throw new RuntimeException("Erro ao ler JSON: " + path, e);
        }
    }
}
