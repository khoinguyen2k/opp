module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    opens com.example.demo to javafx.fxml;
    exports uet.oop.bomberman;
}