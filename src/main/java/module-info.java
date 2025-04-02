module com.example.cab302week4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.example.cab302week4 to javafx.fxml;
    exports com.example.cab302week4;
    exports com.example.cab302week4.controller;
    opens com.example.cab302week4.controller to javafx.fxml;
    exports com.example.cab302week4.model;
    opens com.example.cab302week4.model to javafx.fxml;
}