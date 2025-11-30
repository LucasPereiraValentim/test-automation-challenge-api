package com.test.automation.challenge.api.enums;

public enum StatusCode {

    SUCCESS_OK(200, ""),

    SUCESS_CREATED(201, ""),

    UNAUTHORIZED(401, "Email e/ou senha inválidos"),

    BAD_REQUEST_EMAIL_INVALID(400, "email deve ser um email válido"),

    BAD_REQUEST_EMAIL_IS_EMPTY(400, "email não pode ficar em branco"),

    BAD_REQUEST_PASSWORD_IS_EMPTY(400, "password não pode ficar em branco"),

    BAD_REQUEST_EMAIL_IS_NULL(400, "email deve ser uma string"),

    BAD_REQUEST_PASSWORD_IS_NULL(400, "password deve ser uma string");


    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
