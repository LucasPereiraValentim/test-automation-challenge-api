package com.test.automation.challenge.api.enums;

public enum StatusCode {

    SUCCESS_OK(200, ""),

    SUCESS_CREATED(201, ""),

    UNAUTHORIZED(401, "Email e/ou senha inválidos"),

    BAD_REQUEST_EMAIL_INVALID(400, "email deve ser um email válido"),

    BAD_REQUEST_EMAIL_IS_EMPTY(400, "email não pode ficar em branco"),

    BAD_REQUEST_PASSWORD_IS_EMPTY(400, "password não pode ficar em branco"),

    BAD_REQUEST_EMAIL_IS_NULL(400, "email deve ser uma string"),

    BAD_REQUEST_PASSWORD_IS_NULL(400, "password deve ser uma string"),

    SUCCESS_CREATED_REGISTER_PRODUCT(201, "Cadastro realizado com sucesso"),

    BAD_REQUEST_REGISTER_DUPLICATE_PRODUCT(400, "Já existe produto com esse nome"),

    UNAUTHORIZED_AUTHORIZATION(401, "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"),

    FORBIDDEN_NON_ADMIN_USER(403, "Rota exclusiva para administradores"),

    BAD_REQUEST_PRICE_IS_INVALID(400, "preco deve ser um número"),

    BAD_REQUEST_QUANTITY_IS_INVALID(400, "quantidade deve ser um número");


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
