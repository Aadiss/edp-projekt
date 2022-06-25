package com.example.edpprojekt2.credentials;

import com.example.edpprojekt2.couriermail.CourierMail;
import com.example.edpprojekt2.mongodb.MongoAdapter;
import com.example.edpprojekt2.mongodb.UserDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.google.common.hash.Hashing;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RegisterController {

    private MongoAdapter mongoAdapter = new MongoAdapter();

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorsLabel;

    @FXML
    protected void onRegisterButtonClick() throws IOException{
        ValidationResult validationResult = validateRegister();

        if(validationResult.getStatus().equals(Status.OK)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setContentText("Successfully registered!\nNow log in and enjoy!");
            alert.showAndWait();

            Stage stage = (Stage) errorsLabel.getScene().getWindow();

            stage.close();
        }else {
            this.errorsLabel.setText(validationResult.getErrorDescription());
            this.errorsLabel.setVisible(true);
            this.usernameField.clear();
            this.emailField.clear();
            this.passwordField.clear();
            this.confirmPasswordField.clear();
        }
    }

    private ValidationResult validateRegister() throws IOException {
        if(this.usernameField.getText().length() < 6){
            return new ValidationResult(Status.INVALID_USERNAME);
        }

        if(!this.emailField.getText().contains("@")){
            return new ValidationResult(Status.INCORRECT_EMAIL);
        }

        if(this.passwordField.getText().length() < 6){
            return new ValidationResult(Status.INVALID_PASSWORD);
        }

        if(!this.passwordField.getText().equals(this.confirmPasswordField.getText())){
            return new ValidationResult(Status.PASSWORDS_DOES_NOT_MATCH);
        }

        if(!this.mongoAdapter.checkUserExistence(this.usernameField.getText(), this.emailField.getText())){
            return new ValidationResult(Status.USED_EMAIL_OR_USERNAME);
        }

        if(!CourierMail.sendMail(this.emailField.getText(), this.usernameField.getText())){
            return new ValidationResult(Status.EMAIL_DOES_NOT_EXIST);
        }


        String hashedPassword = Hashing.sha256()
                .hashString(this.passwordField.getText(), StandardCharsets.UTF_8)
                .toString();
        UserDTO userDTO = new UserDTO(this.usernameField.getText(), this.emailField.getText(), hashedPassword);
        this.mongoAdapter.insertUser(userDTO.toDocument());

        return new ValidationResult(Status.OK);
    }
}
