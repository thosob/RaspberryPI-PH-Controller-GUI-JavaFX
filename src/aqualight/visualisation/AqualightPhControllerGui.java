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
     * @brief name of the probes
     */
    @FXML
    private Label p1Name;
    @FXML
    private Label p2Name;
    @FXML
    private Label p3Name;
    @FXML
    private Label p4Name;
    @FXML
    private Label p5Name;
    @FXML
    private Label p6Name;
    
    /**
     * @brief values of the probes
     */
    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    @FXML
    private Label p4;
    @FXML
    private Label p5;
    @FXML 
    private Label p6;
    
    @FXML
    private Label temp1;
    @FXML
    private Label temp2;
    @FXML
    private Label t1;
    @FXML
    private Label t2;
    
    
    /**
     * Explanation:
     *  There are two types of labels. Labels with names and labels with 
     *  values. Labels with names are now known as nameLabels. Labels with
     *  values are now known as valueLabels. In the maps hardware addresses 
     *  are the keys for the labels.
     */    
    private LinkedList<LabelContainer> LabelContainer = new LinkedList<>();
    
    /**
     * @brief contains address as key and position of label in array
     */
    private HashMap<Integer, String> LabelAddress = new HashMap<>();    
    /**
     * @brief contains address as key and name
     */
    private HashMap<String, String> LabelNames = new HashMap<>();
 
        
    
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
    /**
     * @brief initialize all global gui objects, scene has to be load fully
     * @param scene contains fully load scene      
     **/
    public void initializeGlobalGUIObjects(Scene scene){
        //load assingments from disk
        LabelAddress = LoadMapFromDisk("LabelAddress", getLabelAddress());
        LabelNames = LoadMapFromDisk("LabelNames", getLabelNames());
        //Adding all labels to our label container
        LabelContainer lc1 = new LabelContainer(1, p1, p1Name, getLabelAddress().get(1));                                
        String tmpAddress = getLabelAddress().get(1);
        p1Name.setText(LabelNames.get(tmpAddress));
        LabelContainer.add(lc1);
        LabelContainer lc2 = new LabelContainer(2, p2, p2Name, getLabelAddress().get(2));                                
        p1Name.setText(getLabelNames().get((getLabelAddress().get(2))));
        LabelContainer.add(lc2);
        LabelContainer lc3 = new LabelContainer(3, p3, p3Name, getLabelAddress().get(3));      
        p1Name.setText(getLabelNames().get((getLabelAddress().get(3))));
        LabelContainer.add(lc3);
        LabelContainer lc4 = new LabelContainer(4, p4, p4Name, getLabelAddress().get(4));                                
        p1Name.setText(getLabelNames().get((getLabelAddress().get(4))));
        LabelContainer.add(lc4);
        LabelContainer lc5 = new LabelContainer(5, p5, p5Name, getLabelAddress().get(5));    
        p1Name.setText(getLabelNames().get((getLabelAddress().get(5))));
        LabelContainer.add(lc5);
        LabelContainer lc6 = new LabelContainer(6, p6, p6Name, getLabelAddress().get(6));      
        p1Name.setText(getLabelNames().get((getLabelAddress().get(6))));
        LabelContainer.add(lc6);        
        LabelContainer lc7 = new LabelContainer(7, t1, temp1, getLabelAddress().get(7)); 
        p1Name.setText(getLabelNames().get((getLabelAddress().get(7))));
        LabelContainer.add(lc7);
        LabelContainer lc8 = new LabelContainer(8, t2, temp2, getLabelAddress().get(8));    
        p1Name.setText(getLabelNames().get((getLabelAddress().get(8))));
        LabelContainer.add(lc8);
    }
    

    @Override
    public void start(Stage stage) throws Exception {                      
        Parent root = FXMLLoader.load(getClass().getResource("PhControl.fxml"));
        scene = new Scene(root);        
        scene.getStylesheets().add("AqualightPh.css");
        stage.setScene(scene);
        initializeGlobalGUIObjects(scene);
        loadValuesIntoGUI();                    
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
     * @param LabelIndex
     * @param labelName  name of the label             
     * @brief Sets up label for probe
     * @param address of the probe     
     */
    public void SetUpLabel(int LabelIndex, String labelName, String address) {
                
        //Update key value of the map
        if (address != null & labelName != null) {                                   
            getLabelAddress().put(LabelIndex, address);                
        }
        WriteMapToDisk("LabelAddress", getLabelAddress());
    }    

    
    /**
     * @brief gets the name of the label
     * @param address     
     */
    public void SetLabelName(String address, String name){
        for(LabelContainer l : LabelContainer){
            if(l.getAddress().equals(address)){
                l.getNameLabel().setText(name);
            }
        }                
    }
    
    /**
     * @brief gets the name of the label
     * @param address     
     */
    public void SetLabelValue(String address, String value){
        for(LabelContainer l : LabelContainer){
            if(l.getAddress().equals(address)){
                l.getValueLabel().setText(value);
            }
        }                
    }
        

    /**
     * @brief loads values into the gui from sqlite database
     */
    private void loadValuesIntoGUI() {
        
        ControlValueDispatcher dis = new ControlValueDispatcher();
        //Start another thread, so the getting control values does not block the gui
        Thread t = new Thread(dis);                
        t.start();        
        //Use control value listener to listen to changes in the probe data
        ControlValueListener listener = new ControlValueListener();                       
        dis.addObserver(listener);
        int observers = dis.countObservers();
        dis.hasChanged();
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

    /**
     * @return the LabelAddress
     */
    public HashMap<Integer, String> getLabelAddress() {
        if(LabelAddress.size() == 0){
            this.LabelAddress = LoadMapFromDisk("LabelAddress", this.LabelAddress);
        }
        return this.LabelAddress;
    }

    /**
     * @return the LabelNames
     */
    public HashMap<String, String> getLabelNames() {
        if(LabelNames.size() == 0){
            this.LabelNames = LoadMapFromDisk("LabelNames", this.LabelNames);
        }
        return this.LabelNames;
    }

    

}
