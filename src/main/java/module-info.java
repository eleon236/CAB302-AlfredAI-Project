module com.example.alfredAI {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires com.google.gson;
    requires java.sql;


    opens com.example.alfredAI to javafx.fxml;
    exports com.example.alfredAI;
    exports com.example.alfredAI.controller;
    opens com.example.alfredAI.controller to javafx.fxml;
    exports com.example.alfredAI.model;
    opens com.example.alfredAI.model to javafx.fxml;
}