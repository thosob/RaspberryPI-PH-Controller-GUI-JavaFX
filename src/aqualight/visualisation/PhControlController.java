/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.dataprocessing.ControlValueDispatcher;
import aqualight.dataprocessing.ControlValueListener;
import static aqualight.visualisation.AqualightPhControllerGui.LoadMapFromDisk;
import static aqualight.visualisation.AqualightPhControllerGui.WriteMapToDisk;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
     * @brief contains address as key and position of label in array
     */
    private HashMap<Integer, String> LabelAddress = new HashMap<>();    
    /**
     * @brief contains address as key and name
     */
    private HashMap<String, String> LabelNames = new HashMap<>();
 
        
    
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
        System.out.println("You clicked me!");
        label.setText("Hello World!");
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
        tilePane.getChildren().add(createTile("Statistik", Color.TURQUOISE));
        tilePane.getChildren().add(createTile("Einstellung", Color.DARKBLUE));
        
        initializeGlobalGUIObjects(AqualightPhControllerGui.getScene());
        loadValuesIntoGUI();             
        
        //Adding all labels to our label container
        LabelContainer lc1 = new LabelContainer(1, p1Name, p1, getLabelAddress().get(1));                                
        String tmpAddress = getLabelAddress().get(1);
        p1Name.setText(LabelNames.get(tmpAddress));
        LabelContainer.add(lc1);
        LabelContainer lc2 = new LabelContainer(2,  p2Name, p2,getLabelAddress().get(2));                                
        p2Name.setText(getLabelNames().get((getLabelAddress().get(2))));
        LabelContainer.add(lc2);
        LabelContainer lc3 = new LabelContainer(3,  p3Name, p3,getLabelAddress().get(3));      
        p3Name.setText(getLabelNames().get((getLabelAddress().get(3))));
        LabelContainer.add(lc3);
        LabelContainer lc4 = new LabelContainer(4,  p4Name,p4, getLabelAddress().get(4));                                
        p4Name.setText(getLabelNames().get((getLabelAddress().get(4))));
        LabelContainer.add(lc4);
        LabelContainer lc5 = new LabelContainer(5, p5Name,p5,  getLabelAddress().get(5));    
        p5Name.setText(getLabelNames().get((getLabelAddress().get(5))));
        LabelContainer.add(lc5);
        LabelContainer lc6 = new LabelContainer(6,  p6Name,p6, getLabelAddress().get(6));      
        p6Name.setText(getLabelNames().get((getLabelAddress().get(6))));
        LabelContainer.add(lc6);        
        LabelContainer lc7 = new LabelContainer(7,  temp1, t1,getLabelAddress().get(7)); 
        temp1.setText(getLabelNames().get((getLabelAddress().get(7))));
        LabelContainer.add(lc7);
        LabelContainer lc8 = new LabelContainer(8,  temp2, t2,getLabelAddress().get(8));    
        temp2.setText(getLabelNames().get((getLabelAddress().get(8))));
        LabelContainer.add(lc8);
        PHControl = this;
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
                AqualightPhControllerGui.setScene(new Scene(root));
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Eichen")) {
                Parent root = FXMLLoader.load(getClass().getResource("Calibration.fxml"));
                 
                AqualightPhControllerGui.setScene(new Scene(root));
                scene = AqualightPhControllerGui.getScene();
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Statistik")) {
               Parent root = FXMLLoader.load(getClass().getResource("Statistic.fxml"));
                 
                AqualightPhControllerGui.setScene(new Scene(root));
                scene = AqualightPhControllerGui.getScene();
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
            if (menuTitle.equals("Einstellung")) {
                Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
                 
                AqualightPhControllerGui.setScene(new Scene(root));
                scene = AqualightPhControllerGui.getScene();
                scene.getStylesheets().add("AqualightPh.css");
                AqualightPhControllerGui.getGuiStage().setScene(AqualightPhControllerGui.getScene());
                System.out.println(menuTitle);
                return true;
            }
        } catch (IOException exc) {
            System.err.println(exc);
        }
        System.out.println("Could not determine menu title");
        return false;
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
     * @param name sets up the name of the label
     * @brief gets the name of the label
     * @param address sets up the address of the label
     */
    public void SetLabelName(String address, String name){
        for(LabelContainer l : LabelContainer){
            if(l.getAddress().equals(address)){
                l.getNameLabel().setText(name);
            }
        } 
        WriteMapToDisk("LabelNames", getLabelAddress());
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
    
      /**
     * @brief initialize all global gui objects, scene has to be load fully
     * @param scene contains fully load scene      
     **/
    public void initializeGlobalGUIObjects(Scene scene){
        //load assingments from disk
        LabelAddress = LoadMapFromDisk("LabelAddress", getLabelAddress());
        LabelNames = LoadMapFromDisk("LabelNames", getLabelNames());
       
    }

}
