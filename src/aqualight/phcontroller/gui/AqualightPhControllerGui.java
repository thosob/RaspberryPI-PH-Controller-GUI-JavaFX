/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;


import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @brief Starts the Application
 * @author Thomas Sboieroy
 */
public class AqualightPhControllerGui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PhControl.fxml"));
        
        
        Scene scene = new Scene(root);        
        scene.getStylesheets().add("AqualightPh.css");
               
        
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @brief Main method, which is called
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        //Startup with new data handler map, to access
        DataHandler.DataHandlerList = new HashMap<>();
        
        //Start runnable thread, such that database will be read every minute and
        //new data is stored in static model
        //executor.scheduleAtFixedRate(new ReadProbeData() , 0, 1, TimeUnit.MINUTES);
        ReadProbeData data = new ReadProbeData();
        data.run();        
        
        launch(args);
        
        
    }
    
}
