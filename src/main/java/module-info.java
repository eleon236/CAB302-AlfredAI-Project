module com.example.cab302week4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.cab302week4 to javafx.fxml;
    exports com.example.cab302week4;
}