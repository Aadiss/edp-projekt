package com.example.edpprojekt2.credentials;

import com.example.edpprojekt2.mongodb.MongoAdapter;
import com.example.edpprojekt2.mongodb.UserDTO;
import com.google.common.hash.Hashing;
import javafx.fxml.FXML;
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setContentText("Successfully logged!\nNow play and enjoy!");
            alert.showAndWait();

            Stage stage = (Stage) errorsLabel.getScene().getWindow();

            stage.close();
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
