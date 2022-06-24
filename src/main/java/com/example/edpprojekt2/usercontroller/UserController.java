package com.example.edpprojekt2.usercontroller;

import com.example.edpprojekt2.HelloApplication;
import com.example.edpprojekt2.credentials.LoginStateSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    private LoginStateSingleton loginStateSingleton;

    @FXML
    private Label welcomeUserLabel;

    @FXML
    private Label lastLoggedLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginStateSingleton = LoginStateSingleton.getInstance();

        this.welcomeUserLabel.setText("Welcome " + loginStateSingleton.getLoggedUser().getUsername());
        this.lastLoggedLabel.setText(loginStateSingleton.getLoggedUser().getLastLogged());
    }

    @FXML
    protected void onUserGamesClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("recent-user-games.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);
        Stage stage = new Stage();
        stage.setTitle("Recent Games!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onAllGamesClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("recent-games.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1100, 720);
        Stage stage = new Stage();
        stage.setTitle("Recent Games!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onPlayMacauClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("macau.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Macau!");
        stage.setScene(scene);
        stage.show();
    }
}
