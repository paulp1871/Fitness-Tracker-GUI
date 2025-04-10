module com.example.demo03 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo03 to javafx.fxml;
    exports com.example.demo03;
}