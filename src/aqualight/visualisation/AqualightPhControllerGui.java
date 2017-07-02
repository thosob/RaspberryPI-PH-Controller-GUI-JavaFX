/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.IProbe;
import aqualight.databastraction.Probes;
import aqualight.dataprocessing.ControlValueDispatcher;
import aqualight.dataprocessing.ControlValueListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @brief Starts the Application
 * @author Thomas Sboieroy
 */
public class AqualightPhControllerGui extends Application {
    
    public static AqualightPhControllerGui GUI = null;
    
    /**
     * @brief contains the scene, which can be used
     */
    private static Scene scene;    
    private static Stage guiStage;
    
   
    
    
    
    /**
     * @brief assign itself such, that it is globally usable
     */
    public AqualightPhControllerGui(){
        GUI = this;
    }
    
    /**
     * @brief writes mapping to disk
     * @string which data to save (probe, temp, whatever)
     * @return true if successfully executed
     */
    public static boolean WriteMapToDisk(String data, HashMap object) {

        try {
            //Try with ressource writer, object outputstream
            try (FileOutputStream Writer = new FileOutputStream(data); ObjectOutputStream obj = new ObjectOutputStream(Writer)) {
                obj.writeObject(object);
                obj.flush();
            }
            return true;
        } catch (IOException e) {
            //if something goes terrible wrong
            System.err.println(e);
            return false;
        }
    }

    /**
     * @brief reads map from disk
     * @param data filename of the map
     * @param object global variable that is to be replaced with the loaded data
     * @return
     */
    public static HashMap LoadMapFromDisk(String data, HashMap object) {
        try {
            try (FileInputStream fin = new FileInputStream(data); ObjectInputStream ois = new ObjectInputStream(fin)) {
                object = (HashMap) ois.readObject();
            }
            return object;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
  
    

    @Override
    public void start(Stage stage) throws Exception {                      
        Parent root = FXMLLoader.load(getClass().getResource("PhControl.fxml"));
        scene = new Scene(root);        
        scene.getStylesheets().add("AqualightPh.css");
        stage.setScene(scene);       
        setGuiStage(stage);
        //Showing the graphical user interface
        stage.show();
        
        

    }

    /**
     * @brief Main method, which is called
     * @param args the command line arguments
     */
    public static void main(String[] args) {                    
        
        launch(args);

    }

    

    /**
     * @return the scene
     */
    public static Scene getScene() {
        return scene;
    }

    /**
     * @param aScene the scene to set
     */
    public static void setScene(Scene aScene) {
        scene = aScene;
    }

    /**
     * @return the guiStage
     */
    public static Stage getGuiStage() {
        return guiStage;
    }

    /**
     * @param aGuiStage the guiStage to set
     */
    public static void setGuiStage(Stage aGuiStage) {
        guiStage = aGuiStage;
    }

   

    

}
