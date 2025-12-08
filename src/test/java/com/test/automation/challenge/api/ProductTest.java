package com.test.automation.challenge.api;


import com.test.automation.challenge.api.enums.StatusCode;
import com.test.automation.challenge.api.logic.login.LoginLogic;
import com.test.automation.challenge.api.logic.product.ProductLogic;
import com.test.automation.challenge.api.logic.user.UserLogic;
import com.test.automation.challenge.api.utils.APIUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

@Epic("Product")
@Feature("Product")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest {

    private LoginLogic loginLogic;

    private ProductLogic registerProduct;

    private UserLogic createUserLogic;

    public ProductTest() {
        this.loginLogic         = new LoginLogic();
        this.registerProduct    = new ProductLogic();
        this.createUserLogic    = new UserLogic();

    }

    @Story("Realizar cadastro de produto com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(1)
    @DisplayName("CT01 - Realizar cadastro de produto com sucesso")
    public void performLRegisterProductSuccessTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "performLRegisterProductSuccessTest");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .assertRegisterProductSuccess(StatusCode.SUCCESS_CREATED_REGISTER_PRODUCT.getCode(),
                            StatusCode.SUCCESS_CREATED_REGISTER_PRODUCT.getMessage(),
                            "message", "_id")
                    .getListProducts().assertListProductAfter(StatusCode.SUCCESS_OK.getCode());
        });
    }


    @Story("Realizar cadastro de produto já existente na base de dados")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(2)
    @DisplayName("CT02 - Tentativa de cadastro de produto duplicado")
    public void registerDuplicateProductTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerDuplicateProductTest");
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
    public void registerProductWithInvalidTokenTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithInvalidTokenTest");
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
    public void registerProductWithEmptyTokenTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithEmptyTokenTest");
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
    public void registerProductWithNullTokenTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithEmptyTokenTest");
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
    public void registerProductWithNonAdminUserTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithNonAdminUserTest");
        Allure.step(scenario, () -> {
            this.createUserLogic.performCreateUser(scenario);
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
    public void registerProductWithAlphanumericPriceTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithAlphanumericPriceTest");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.BAD_REQUEST_PRICE_IS_INVALID.getCode(),
                            StatusCode.BAD_REQUEST_PRICE_IS_INVALID.getMessage(), "preco");
        });
    }

    @Story("Realizar cadastro de produto passando caracteres alfanúmericos no campo de quantidadede")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(8)
    @DisplayName("CT08 - Realizar cadastro de produto passando caracteres alfanúmericos no campo de quantidade")
    public void registerProductWithAlphanumericQuantityTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "registerProductWithAlphanumericQuantityTest");
        Allure.step(scenario, () -> {
            this.loginLogic
                    .performLogin(scenario)
                    .assertLoginSuccess(StatusCode.SUCCESS_OK.getCode());
            this.registerProduct.performRegisterProduct(scenario)
                    .asseertRegisterProductInvalid(StatusCode.BAD_REQUEST_QUANTITY_IS_INVALID.getCode(),
                            StatusCode.BAD_REQUEST_QUANTITY_IS_INVALID.getMessage(), "quantidade");
        });
    }

    @Story("Buscar produto por ID")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(8)
    @DisplayName("CT09 - Buscar produto por ID")
    public void findProductByIdTest() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "findProductByIdTest");
        Allure.step(scenario, () -> {
            this.registerProduct.getListProducts().getProductById().assertFindByIdProduct(StatusCode.SUCCESS_OK.getCode());
        });
    }
}
