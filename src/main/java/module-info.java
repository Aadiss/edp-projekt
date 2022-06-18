module com.example.edpprojekt2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    exports com.example.edpprojekt2;
    exports com.example.edpprojekt2.macaucontroller;
    opens com.example.edpprojekt2.macaucontroller to javafx.fxml;
    opens com.example.edpprojekt2 to javafx.fxml;
}