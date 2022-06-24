package com.example.edpprojekt2.credentials;

import com.example.edpprojekt2.HelloApplication;
import com.example.edpprojekt2.mongodb.MongoAdapter;
import com.example.edpprojekt2.mongodb.UserDTO;
import com.google.common.hash.Hashing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LoginController {

    private MongoAdapter mongoAdapter = new MongoAdapter();

    private LoginStateSingleton loginStateSingleton = LoginStateSingleton.getInstance();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorsLabel;

    @FXML
    protected void onLoginButtonClick() throws IOException{
        ValidationResult validationResult = validateLogin();

        if(validationResult.getStatus().equals(Status.OK)){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-dashboard-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle(loginStateSingleton.getLoggedUser().getUsername() + " Dashboard");
            stage.setScene(scene);
            stage.show();

            Stage stageLogin = (Stage) errorsLabel.getScene().getWindow();

            stageLogin.close();
        }
        else {
            this.errorsLabel.setText(validationResult.getErrorDescription());
            this.errorsLabel.setVisible(true);
            this.usernameField.clear();
            this.passwordField.clear();
        }
    }

    private ValidationResult validateLogin(){
        if(this.usernameField.getText().length() < 6){
            return new ValidationResult(Status.INVALID_USERNAME);
        }

        if(this.passwordField.getText().length() < 6){
            return new ValidationResult(Status.INVALID_PASSWORD);
        }

        UserDTO userDTO = this.mongoAdapter.getUser(this.usernameField.getText());
        if(userDTO == null){
            return new ValidationResult(Status.USER_NOT_FOUND);
        }else {
            if(validatePassword(userDTO.getPassword())){
                userDTO.setLastLogged();
                loginStateSingleton.setLoggedUser(userDTO);
            }else {
                return new ValidationResult(Status.INCORRECT_PASSWORD);
            }
        }

        return new ValidationResult(Status.OK);
    }

    private boolean validatePassword(String passwordToVerify){
        String hashedPassword = Hashing.sha256()
                .hashString(this.passwordField.getText(), StandardCharsets.UTF_8)
                .toString();

        return hashedPassword.equals(passwordToVerify);
    }
}
