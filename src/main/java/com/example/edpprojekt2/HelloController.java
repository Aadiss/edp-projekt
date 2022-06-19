package com.example.edpprojekt2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("macau.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        Stage stage = new Stage();
        stage.setTitle("Macau!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void showRecentGames() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("recent-games.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1180, 720);
        Stage stage = new Stage();
        stage.setTitle("Recent Games!");
        stage.setScene(scene);
        stage.show();
    }
}