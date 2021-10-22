module cr.ac.una.restuna {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.logging;
    requires com.jfoenix;
    requires jakarta.ws.rs;
    requires jakarta.xml.bind;
    requires jakarta.json;
    requires java.base;
    requires org.controlsfx.controls;

    requires java.desktop;

    opens cr.ac.una.restuna to javafx.fxml, javafx.graphics;
    opens cr.ac.una.restuna.controller to javafx.fxml , javafx.controls , com.jfoenix;
    
    exports cr.ac.una.restuna.model;
//    exports cr.ac.una.restuna;
}
