module com.example.edpprojekt2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires retrofit2;
    requires com.google.gson;
    requires okhttp3;
    requires retrofit2.converter.gson;
    requires com.google.common;

    exports com.example.edpprojekt2.macaucontroller;
    exports com.example.edpprojekt2;
    exports com.example.edpprojekt2.currencyapi;
    opens com.example.edpprojekt2.macaucontroller to javafx.fxml;
    opens com.example.edpprojekt2 to javafx.fxml;
    opens com.example.edpprojekt2.currencyapi to com.google.gson;
    opens com.example.edpprojekt2.recentgames to javafx.fxml;
    opens com.example.edpprojekt2.mongodb to javafx.base;
    opens com.example.edpprojekt2.credentials;
}