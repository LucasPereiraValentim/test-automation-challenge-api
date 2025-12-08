package com.test.automation.challenge.api;

import com.test.automation.challenge.api.enums.StatusCode;
import com.test.automation.challenge.api.logic.user.UserLogic;
import com.test.automation.challenge.api.utils.APIUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

@Epic("User")
@Feature("User")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private UserLogic userLogic;

    public UserTest() {
        this.userLogic = new UserLogic();
    }

    @Story("Listar usuários com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(1)
    @DisplayName("CT01 - Listar usuários com sucesso")
    public void listUsersSuccess() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "listUsersSuccess");
        Allure.step(scenario, () -> {
            this.userLogic.listUsers()
                    .assertListUsersSuccess(StatusCode.SUCCESS_OK.getCode(), "usuarios");

        });
    }

    @Story("Cadastrar usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(2)
    @DisplayName("CT02 - Cadastrar usuário com sucesso")
    public void createUser() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "createUser");
        Allure.step(scenario, () -> {
            this.userLogic.performCreateUser(scenario)
                    .assertRequestSuccess(StatusCode.SUCESS_CREATED.getCode(), StatusCode.SUCESS_CREATED.getMessage(), "message")
                    .listUsers()
                    .assertAtributeListUsers(StatusCode.SUCCESS_OK.getCode());

        });
    }

    @Story("Retorna os dados de um usuário específico")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(3)
    @DisplayName("CT03 - Retorna os dados de um usuário específico")
    public void returnUserById() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "returnUserById");
        Allure.step(scenario, () -> {
            this.userLogic.performCreateUser(scenario)
                    .getUserById()
                    .assertReturnUserSuccess(StatusCode.SUCCESS_OK.getCode());
        });
    }

    @Story("Excluir usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(4)
    @DisplayName("CT04 - Excluir usuário com sucesso")
    public void deleteUserById() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "deleteUserById");
        Allure.step(scenario, () -> {
            this.userLogic.performCreateUser(scenario)
                    .deleteUserById()
                    .assertRequestSuccess(StatusCode.SUCCESS_OK_DELETE_SUCCESS.getCode(),
                            StatusCode.SUCCESS_OK_DELETE_SUCCESS.getMessage(), "message");
        });
    }

    @Story("Alterar usuário com sucesso")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(5)
    @DisplayName("CT05 - Alterar usuário com sucesso")
    public void updateUserById() {
        String scenario = APIUtils.getDisplayName(this.getClass(), "updateUserById");
        Allure.step(scenario, () -> {
            this.userLogic.performCreateUser(scenario)
                    .updateUserById()
                    .assertRequestSuccess(StatusCode.SUCCESS_OK_UPDATE_SUCCESS.getCode(),
                            StatusCode.SUCCESS_OK_UPDATE_SUCCESS.getMessage(),
                            "message")
                    .listUsers()
                    .assertAtributeListUsers(StatusCode.SUCCESS_OK.getCode());
        });
    }
}
