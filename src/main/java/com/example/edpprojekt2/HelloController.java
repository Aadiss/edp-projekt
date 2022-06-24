package com.example.edpprojekt2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onLoginButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Recent Games!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onRegisterButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("register-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 601);
        Stage stage = new Stage();
        stage.setTitle("Recent Games!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onDetailsButton() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Project Details");
        alert.setContentText("This is simple Macau game project created by:\n" +
                "Adrian Stadnik WCY19IJ4S1");
        alert.showAndWait();
    }
}