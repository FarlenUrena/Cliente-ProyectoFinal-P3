module cr.ac.una.restuna {
    requires javafx.controls;
    requires javafx.fxml;  
    requires javafx.graphics;
  
    requires java.logging;
    requires com.jfoenix;
    requires jakarta.ws.rs;
    requires jakarta.xml.bind;
    requires jakarta.json;
    requires java.base;
    requires org.controlsfx.controls;

    requires java.desktop;

    opens cr.ac.una.restuna to javafx.fxml, javafx.graphics, org.controlsfx.controls;
    opens cr.ac.una.restuna.controller to javafx.fxml , javafx.controls , com.jfoenix,org.controlsfx.controls;
    
    exports cr.ac.una.restuna.model;
//    exports cr.ac.una.restuna;
}
