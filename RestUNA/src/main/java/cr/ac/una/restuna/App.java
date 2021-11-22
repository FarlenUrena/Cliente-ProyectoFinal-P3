package cr.ac.una.restuna;

import cr.ac.una.restuna.util.FlowController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
       FlowController.getInstance().InitializeFlow(stage,null);
       stage.setTitle("Restaurante UNA");
       FlowController.getInstance().setLang("ESP");
       FlowController.getInstance().goViewInWindow("LoginView");
    
    }
    public static void main(String[] args) {
        launch();
    }

}