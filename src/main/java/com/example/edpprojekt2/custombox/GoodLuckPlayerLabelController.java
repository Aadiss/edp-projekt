package com.example.edpprojekt2.custombox;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GoodLuckPlayerLabelController implements Initializable
{
    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

    public Label getUsernameLabel()
    {
        return usernameLabel;
    }

    public void setUsernameLabel(String usernameLabel)
    {
        this.usernameLabel.setText(usernameLabel);
    }
}
