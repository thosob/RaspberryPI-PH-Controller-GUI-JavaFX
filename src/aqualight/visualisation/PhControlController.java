/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.databastraction.IProbeData;
import aqualight.databastraction.PhProbe;
import aqualight.databastraction.Probes;
import aqualight.dataprocessing.ControlValueDispatcher;
import aqualight.dataprocessing.ControlValueListener;
import static aqualight.visualisation.AqualightPhControllerGui.LoadMapFromDisk;
import static aqualight.visualisation.AqualightPhControllerGui.WriteMapToDisk;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author thoma
 */
public class PhControlController implements Initializable {

    
    public static PhControlController PHControl;
    /**
     * Explanation:
     *  There are two types of labels. Labels with names and labels with 
     *  values. Labels with names are now known as nameLabels. Labels with
     *  values are now known as valueLabels. In the maps hardware addresses 
     *  are the keys for the labels.
     */    
    private LinkedList<LabelContainer> LabelContainer = new LinkedList<>();
          
    /**
     * @brief contains address as key and name
     */
    private HashMap<Integer, String> LabelNames = new HashMap<>();
 
        
    
    @FXML
    private Label label;

    @FXML
    private TilePane tilePane;

     /**
     * @brief name of the probes
     */
    @FXML
    public Label p1Name;
    @FXML
    public Label p2Name;
    @FXML
    public Label p3Name;
    @FXML
    public Label p4Name;
    @FXML
    public Label p5Name;
    @FXML
    public Label p6Name;
    
    /**
     * @brief values of the probes
     */
    @FXML
    public Label p1;
    @FXML
    public Label p2;
    @FXML
    public Label p3;
    @FXML
    public Label p4;
    @FXML
    public Label p5;
    @FXML 
    public Label p6;
    
    @FXML
    public Label temp1;
    @FXML
    public Label temp2;
    @FXML
    public Label t1;
    @FXML
    public Label t2;
    
   
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tilePane.setPadding(new Insets(5, 0, 5, 0));
        tilePane.setVgap(4);
        tilePane.setHgap(4);
        tilePane.setPrefColumns(4);

        tilePane.getChildren().add(new Label());
        tilePane.getChildren().add(new Label());
        tilePane.getChildren().add(createTile("Übersicht", Color.CADETBLUE));
        tilePane.getChildren().add(createTile("Eichen", Color.DIMGREY));
        tilePane.getChildren().add(createTile("Kamera", Color.TURQUOISE));
        tilePane.getChildren().add(createTile("Einstellung", Color.DARKBLUE));
        
        initializeGlobalGUIObjects(AqualightPhControllerGui.getScene());
        loadValuesIntoGUI();             
        
        //Adding all labels to our label container
        LabelContainer lc1 = new LabelContainer(0, p1Name, p1, "78");                                        
        p1Name.setText(LabelNames.get(0));
        LabelContainer.add(lc1);
        LabelContainer lc2 = new LabelContainer(1,  p2Name, p2,"79");                                
        p2Name.setText(getLabelNames().get((1)));
        LabelContainer.add(lc2);
        LabelContainer lc3 = new LabelContainer(2,  p3Name, p3,"73");      
        p3Name.setText(getLabelNames().get(2));
        LabelContainer.add(lc3);
        LabelContainer lc4 = new LabelContainer(3,  p4Name,p4, "74");                                
        p4Name.setText(getLabelNames().get(3));
        LabelContainer.add(lc4);
        LabelContainer lc5 = new LabelContainer(4, p5Name,p5,  "76");    
        p5Name.setText(getLabelNames().get(4));
        LabelContainer.add(lc5);
        LabelContainer lc6 = new LabelContainer(5,  p6Name,p6, "77");      
        p6Name.setText(getLabelNames().get(5));
        LabelContainer.add(lc6);        
        LabelContainer lc7 = new LabelContainer(6,  temp1, t1,"/sys/bus/w1/devices/28-05169046aaff/w1_slave"); 
        temp1.setText(getLabelNames().get(6));
        LabelContainer.add(lc7);
        LabelContainer lc8 = new LabelContainer(7,  temp2, t2,"/sys/bus/w1/devices/28-051690467fff/w1_slave");    
        temp2.setText(getLabelNames().get(7));
        LabelContainer.add(lc8);
        PHControl = this;
               
        ArrayList<String> phDropdownlist = new ArrayList<>();
        phDropdownlist.add("73 - "+PhControlController.PHControl.getLabelNamesByAddress(73));   
        phDropdownlist.add("74 - "+PhControlController.PHControl.getLabelNamesByAddress(74));   
        phDropdownlist.add("77 - "+PhControlController.PHControl.getLabelNamesByAddress(77));
        phDropdownlist.add("78 - "+PhControlController.PHControl.getLabelNamesByAddress(78));
        phDropdownlist.add("79 - "+PhControlController.PHControl.getLabelNamesByAddress(79));                                           
    }
    
    
    /**
     * @brief creates a new tile for the page
     * @param label name of the label used
     * @param color color that the tile will have
     * @return stackpane as a tile
     */
    private StackPane createTile(String label, Color color) {
        Rectangle r = new Rectangle();
        r.styleProperty().set("-fx-background-color: aqua");
        r.widthProperty().set(95);
        r.heightProperty().set(95);
        r.idProperty().set(label);
        r.setFill(color);
        Text text = new Text(label);
        text.setFill(Color.WHITE);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(r, text);

        text.setOnTouchPressed((TouchEvent event) -> {
            
            changeScreen(((Text)event.getSource()).getText());
            event.consume();
        });
        text.setOnMouseClicked((MouseEvent event) -> {
            changeScreen(((Text)event.getSource()).getText());
            event.consume();
        });
        
        r.setOnTouchPressed((TouchEvent event) -> {
            changeScreen(((Rectangle) event.getSource()).getId());
            event.consume();
        });
        r.setOnMouseClicked((MouseEvent event) -> {
            changeScreen(((Rectangle) event.getSource()).getId());
            event.consume();
        });
        return stack;
    }

    /**
     * @brief changes the screen to what the menu reads
     * @param menuTitle
     * @return
     */
    private boolean changeScreen(String menuTitle) {
        Scene scene;
        try {
            if (menuTitle.equals("Übersicht")) {
                Parent root = FXMLLoader.load(getClass().getResource("PhControl.fxml"));
                scene = AqualightPhControllerGui.getScene();
                scene.setRoot(root);  
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Eichen")) {
                Parent root = FXMLLoader.load(getClass().getResource("Calibration.fxml"));
                 
                scene = AqualightPhControllerGui.getScene();
                scene.setRoot(root);  
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Kamera")) {
               Parent root = FXMLLoader.load(getClass().getResource("Kamera.fxml"));
                 
                scene = AqualightPhControllerGui.getScene();
                scene.setRoot(root);  
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Einstellung")) {
                Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));                                
                scene = AqualightPhControllerGui.getScene();
                scene.setRoot(root);                                
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
        } catch (IOException exc) {
            System.out.println(exc);
        }
        System.out.println("Could not determine menu title - "+menuTitle);
        return false;
    }
    
    /**
     * @param label
     * @param name sets up the name of the label
     * @brief gets the name of the label
     */
    public void SetLabelName(int label, String name){
        for(LabelContainer l : LabelContainer){
            if(l.getIndex() == label){
                l.getNameLabel().setText(name);
            }
        } 
        HashMap<Integer, String> labelNames = getLabelNames();
        if(labelNames.containsKey(label)){
            labelNames.replace(label, name);
        }
        else{
            labelNames.put(label, name);
        }
        WriteMapToDisk("LabelNames", labelNames);
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
    * @return the LabelNames
    */
    public HashMap<Integer, String> getLabelNames() {
        if(LabelNames.isEmpty()){
            this.LabelNames = LoadMapFromDisk("LabelNames", this.LabelNames);
        }
        return this.LabelNames;
    }
    
    /**
    * @param i as integer
    * @return the Name of the Label
    */
    public String getLabelNamesByAddress(int i) {        
        String address;
        String iString = Integer.toString(i);
        String res = null;
        
        for(LabelContainer lc : this.LabelContainer){
            address = lc.getAddress();
            if(address.equals(iString)){
                res = lc.getNameLabel().getText();
            }
        }
        return res; 
    }
    
    /**
    * @brief initialize all global gui objects, scene has to be load fully
    * @param scene contains fully load scene      
    **/
    public void initializeGlobalGUIObjects(Scene scene){
        //load assingments from disk        
        LabelNames = LoadMapFromDisk("LabelNames", getLabelNames());
       
    }
}
