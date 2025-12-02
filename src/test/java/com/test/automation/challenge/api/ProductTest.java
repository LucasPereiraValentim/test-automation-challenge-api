package com.test.automation.challenge.api;


import com.test.automation.challenge.api.enums.StatusCode;
import com.test.automation.challenge.api.logic.login.LoginLogic;
import com.test.automation.challenge.api.logic.register.RegisterProductLogic;
import com.test.automation.challenge.api.utils.APIUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

@Epic("Product")
@Feature("Product")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    private LoginLogic loginLogic;

    private RegisterProductLogic registerProduct;

    @BeforeEach
    public void before() {
        this.loginLogic         = new LoginLogic();
        this.registerProduct    = new RegisterProductLogic();
    }

    @Story("Realizar cadastro de produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(1)
    @DisplayName("CT01 - Realizar cadastro de produto com sucesso")
    public void performLRegisterProductSuccess() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLRegisterProductSuccess");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .assertRegisterProductSuccess(StatusCode.SUCCESS_CREATED_REGISTER_PRODUCT.getCode(),
                            StatusCode.SUCCESS_CREATED_REGISTER_PRODUCT.getMessage(),
                            "message", "_id");
        });
    }


    @Story("Realizar cadastro de produto já existente na base de dados")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(2)
    @DisplayName("CT02 - Tentativa de cadastro de produto duplicado")
    public void registerDuplicateProduct() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerDuplicateProduct");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.BAD_REQUEST_REGISTER_DUPLICATE_PRODUCT.getCode(),
                            StatusCode.BAD_REQUEST_REGISTER_DUPLICATE_PRODUCT.getMessage(), "message");
        });
    }

    @Story("Realizar cadastro de produto com token inválido")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(3)
    @DisplayName("CT03 - Realizar cadastro de produto com token inválido")
    public void registerProductWithInvalidToken() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithInvalidToken");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.UNAUTHORIZED_AUTHORIZATION.getCode(),
                            StatusCode.UNAUTHORIZED_AUTHORIZATION.getMessage(), "message");
        });
    }

    @Story("Realizar cadastro de produto com token vazio")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(4)
    @DisplayName("CT04 - Realizar cadastro de produto com token vazio")
    public void registerProductWithEmptyToken() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithEmptyToken");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.UNAUTHORIZED_AUTHORIZATION.getCode(),
                            StatusCode.UNAUTHORIZED_AUTHORIZATION.getMessage(), "message");
        });
    }

    @Story("Realizar cadastro de produto com token nulo")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(5)
    @DisplayName("CT05 - Realizar cadastro de produto com token nulo")
    public void registerProductWithNullToken() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithEmptyToken");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.UNAUTHORIZED_AUTHORIZATION.getCode(),
                            StatusCode.UNAUTHORIZED_AUTHORIZATION.getMessage(), "message");
        });
    }

    @Story("Realizar cadastro de produto com usuário sem permissão de admin")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(6)
    @DisplayName("CT06 - Realizar cadastro de produto com usuário sem permissão de admin")
    public void registerProductWithNonAdminUser() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithNonAdminUser");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.FORBIDDEN_NON_ADMIN_USER.getCode(),
                            StatusCode.FORBIDDEN_NON_ADMIN_USER.getMessage(), "message");
        });
    }


    @Story("Realizar cadastro de produto passando caracteres alfanúmericos no campo de preço")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(7)
    @DisplayName("CT07 - Realizar cadastro de produto passando caracteres alfanúmericos no campo de preço")
    public void registerProductWithAlphanumericPrice() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithAlphanumericPrice");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.BAD_REQUEST_PRICE_IS_INVALID.getCode(),
                            StatusCode.BAD_REQUEST_PRICE_IS_INVALID.getMessage(), "preco");
        });
    }

    @Story("Realizar cadastro de produto passando caracteres alfanúmericos no campo de quantidaded")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(8)
    @DisplayName("CT08 - Realizar cadastro de produto passando caracteres alfanúmericos no campo de quantidade")
    public void registerProductWithAlphanumericQuantity() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithAlphanumericQuantity");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.BAD_REQUEST_QUANTITY_IS_INVALID.getCode(),
                            StatusCode.BAD_REQUEST_QUANTITY_IS_INVALID.getMessage(), "quantidade");
        });
    }
}
