package com.example.edpprojekt2.custombox;

import com.example.edpprojekt2.HelloApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GoodLuckPlayerLabelControl extends AnchorPane {
    GoodLuckPlayerLabelController controller;

    StringProperty usernameLabelContent = new SimpleStringProperty("{username}");

    public GoodLuckPlayerLabelControl() throws IOException {
        super();
        initControl();
    }

    private void initControl() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("GoodLuckPlayerLabel.fxml"));
        controller = new GoodLuckPlayerLabelController();
        loader.setController(controller);
        Node node = loader.load();
        this.getChildren().add(node);
        controller.getUsernameLabel().textProperty().bind(usernameLabelContent);
    }

    public void setUsername(String playerName) {
        this.usernameLabelContent.setValue(playerName);
    }
}
