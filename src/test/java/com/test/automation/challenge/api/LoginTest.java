package com.test.automation.challenge.api;

import com.test.automation.challenge.api.enums.StatusCode;
import com.test.automation.challenge.api.logic.login.LoginLogic;
import com.test.automation.challenge.api.utils.APIUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@Epic("Login")
@Feature("Login")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    private LoginLogic loginLogic;

    public LoginTest() {
        this.loginLogic = new LoginLogic();
    }

    @Story("Realizar login com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(1)
    @DisplayName("CT01 - Realizar login com sucesso")
    public void performLoginSuccess() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginSuccess");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
        });
    }

    @Story("Realizar login com usuário inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(2)
    @DisplayName("CT02 - Realizar login com usuário inválido")
    public void performLoginUserInvalid() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginUserInvalid");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.BAD_REQUEST_EMAIL_INVALID.getCode(),
                            StatusCode.BAD_REQUEST_EMAIL_INVALID.getMessage(), "email");
        });
    }

    @Story("Realizar login com senha inválida")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(3)
    @DisplayName("CT03 - Realizar login com senha inválida")
    public void performLoginPasswordInvalid() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginPasswordInvalid");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.UNAUTHORIZED.getCode(),
                            StatusCode.UNAUTHORIZED.getMessage(), "message");
        });
    }

    @Story("Realizar login com usuário vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(4)
    @DisplayName("CT04 - Realizar login com usuário vazio")
    public void performLoginUserEmpty() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginUserEmpty");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.BAD_REQUEST_EMAIL_IS_EMPTY.getCode(),
                            StatusCode.BAD_REQUEST_EMAIL_IS_EMPTY.getMessage(), "email");
        });
    }

    @Story("Realizar login com senha vazia")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(5)
    @DisplayName("CT05 - Realizar login com senha vazia")
    public void performLoginPasswordEmpty() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginPasswordEmpty");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.BAD_REQUEST_PASSWORD_IS_EMPTY.getCode(),
                            StatusCode.BAD_REQUEST_PASSWORD_IS_EMPTY.getMessage(), "password");
        });
    }

    @Story("Realizar login com usuário nulo")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(6)
    @DisplayName("CT06 - Realizar login com usuário nulo")
    public void performLoginUserNull() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginUserNull");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.BAD_REQUEST_EMAIL_IS_NULL.getCode(),
                            StatusCode.BAD_REQUEST_EMAIL_IS_NULL.getMessage(), "email");
        });
    }

    @Story("Realizar login com senha nula")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(7)
    @DisplayName("CT07 - Realizar login com senha nula")
    public void performLoginPasswordNull() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLoginPasswordNull");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .asseertLoginInvalid(StatusCode.BAD_REQUEST_PASSWORD_IS_NULL.getCode(),
                            StatusCode.BAD_REQUEST_PASSWORD_IS_NULL.getMessage(), "password");
        });
    }
}
