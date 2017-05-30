/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.databastraction.ProbeData;
import aqualight.databastraction.ReadProbeData;
import aqualight.dataprocessing.IProbeData;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
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

    /**
     * @brief stores all probe labels with id
     */
    private static HashMap<String, Label> map = new HashMap<String, Label>();
    /**
     * @brief map for temperature labels with id
     */
    private static HashMap<String, Label> tempMap = new HashMap<String, Label>();
    /**
     * @brief probe label names
     */
    private static Label[] labels;
    /**
     * @brief temperature labels
     */
    private static Label[] tempLabels;
    /**
     * @brief probe values     
     */
    private static Label[] labelsName;
    /**
     * @brief temperature values
     */
    private static Label[] tempLabelsName;

    /**
     * @brief writes mapping to disk
     * @string which data to save (probe, temp, whatever)
     * @return true if successfully executed
     */
    private static boolean WriteMapToDisk(String data, HashMap object) {

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
    private static HashMap LoadMapFromDisk(String data, HashMap object) {
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
        int i = 0;
        //Lookup all notes by css class an create a value list
        Set<Node> labelSet = scene.getRoot().lookupAll(".probeValueText");
        //initialize array size
        labels = new Label[labelSet.size()];
        //iterate over all labels in set
        i = 0;
        for (Node n : labelSet) {
            labels[i] = (Label) n;
            i++;
        }
        
        //Lookup all notes by css class and create a label list
        Set<Node> labelSetName = scene.getRoot().lookupAll(".probeLabelText");
        //initialize array size
        labelsName = new Label[labelSetName.size()];
        //iterate over all labels in set
        i = 0;
        for (Node n : labelSetName) {
            labelsName[i] = (Label) n;
            i++;
        }

        //Lookup all temp notes by css class for value list of labels
        Set<Node> tempLabelSet = scene.getRoot().lookupAll(".tempValueText");
        //initialize array size
        tempLabels = new Label[tempLabelSet.size()];
        //iterate over all labels in set
        i = 0;
        for (Node n : tempLabelSet) {
            tempLabels[i] = (Label) n;
            i++;
        }
        
        //Lookup all temp notes by css class to build temperature labels 
        Set<Node> tempLabelNameSet = scene.getRoot().lookupAll(".tempLabelText");
        //initialize array size
        tempLabelsName = new Label[tempLabelNameSet.size()];
        //iterate over all labels in set
        i = 0;
        for (Node n : tempLabelNameSet) {
            tempLabelsName[i] = (Label) n;
            i++;
        }

        //Initialize Hashmaps
        LoadMapFromDisk("map", map);
        LoadMapFromDisk("tempMap", tempMap);
    }
    

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("PhControl.fxml"));
        Scene scene = new Scene(root);        
        scene.getStylesheets().add("AqualightPh.css");
        stage.setScene(scene);
        initializeGlobalGUIObjects(scene);
        loadValuesIntoGUI();
        
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();        
        
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
     * @brief Sets up label for probe
     * @param address of the probe
     * @param label that is used
     */
    public static void SetUpLabel(String address, Label label) {
        //Update key value of the map
        if (address != null & label != null) {
            map.put(address, label);
            //saves data in disk
            WriteMapToDisk("map", map);
        }
    }
    /**
     * @brief sets up label for probe 
     * @param address of the probe
     */
    public static void SetUpLabel(String address){
        //check for address else do nothing more
        if(!map.containsKey(address)){            
            //go over all labels
            for(Label l : labels){
                //check if label is already used
                if(!map.containsValue(l)){
                    //if not set up our mapping
                    map.put(address, l);
                    //everything is fine we've set up the label
                    return;
                }
            }
        }
        WriteMapToDisk("map",map);                
    }

    /**
     * @brief Sets up label for probe
     * @param address of the probe
     * @param label that is used
     */
    public static void SetUpTemperatureLabel(String address, Label label) {
        //Update key value of the map
        if (address != null & label != null) {
            tempMap.put(address, label);
            //saves data in disk
            WriteMapToDisk("tempMap", tempMap);
        }
    }

    /**
     * @brief gets the label
     * @param address address is needed as key
     * @return javafx-label
     */
    public static Label GetLabel(String address) {
        return map.get(address);
    }

    /**
     * @brief gets the temperature label
     * @param id is needed as key
     * @return javafx-label
     */
    public static Label GetTempLabel(String id) {
        return tempMap.get(id);
    }

    /**
     * @brief gets all probe labels
     * @return ProbeLabel Array
     */
    public static Label[] GetAllProbeLabels() {
        return labels;
    }

    /**
     * @brief returns temperature labels
     * @return temperature labels
     */
    public static Label[] GetAllTemperatureLabels() {
        return tempLabels;
    }
    
       public static HashMap<String, Label> getMap() {
        return map;
    }

    public static void setMap(HashMap<String, Label> map) {
        AqualightPhControllerGui.map = map;
    }

    public static HashMap<String, Label> getTempMap() {
        return tempMap;
    }

    public static void setTempMap(HashMap<String, Label> tempMap) {
        AqualightPhControllerGui.tempMap = tempMap;
    }

    public static Label[] getLabels() {
        return labels;
    }

    public static void setLabels(Label[] labels) {
        AqualightPhControllerGui.labels = labels;
    }

    public static Label[] getTempLabels() {
        return tempLabels;
    }

    public static void setTempLabels(Label[] tempLabels) {
        AqualightPhControllerGui.tempLabels = tempLabels;
    }

    public static Label[] getLabelsName() {
        return labelsName;
    }

    public static void setLabelsName(Label[] labelsName) {
        AqualightPhControllerGui.labelsName = labelsName;
    }

    public static Label[] getTempLabelsName() {
        return tempLabelsName;
    }

    public static void setTempLabelsName(Label[] tempLabelsName) {
        AqualightPhControllerGui.tempLabelsName = tempLabelsName;
    }
    /**
     * @brief loads values into the gui from sqlite database
     */
    private void loadValuesIntoGUI() {
        //Startup with new data handler map, to access
        ProbeData.DataHandlerList = new HashMap<>();
        
        //Manually get new data
        ReadProbeData readProbeData = new ReadProbeData();
        readProbeData.run();        
        
        //Get all addresses and display data of all addresses to the frontend
        Iterator it = ProbeData.DataHandlerList.entrySet().iterator();
        
        //iterate over all datahandlers
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            //Get address and list
            String address = (String) pair.getKey();
            LinkedList<IProbeData> list = ((ProbeData) pair.getValue()).Values;

            //get probevalue and display that to the screen
            double value = list.getLast().getProbeValue();
            
            Label label = GetLabel(address);
            if(label == null){
                SetUpLabel(address);
                label = GetLabel(address);
                label.setText(String.valueOf(value));
            }
            else{
                label.setText(String.valueOf(value));
            }           
            //concurrency  protection
            it.remove();
        }
    }

    

}
