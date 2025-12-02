package com.test.automation.challenge.api.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YamlUtils {

    public static String getValueFromYaml(String keyPath) {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlUtils.class.getClassLoader().getResourceAsStream("environment.yaml")) {
            if (in == null) throw new RuntimeException("Arquivo config.yaml não encontrado!");
            Map<String, Object> obj = yaml.load(in);

            String[] keys = keyPath.split("\\.");
            Object value = obj;
            for (String key : keys) {
                if (value instanceof Map) {
                    value = ((Map<?, ?>) value).get(key);
                } else {
                    return null;
                }
            }
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o YAML", e);
        }
    }
}

