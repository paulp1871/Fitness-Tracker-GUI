module com.example.demo03 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo03 to javafx.fxml;
    exports com.example.demo03;
}