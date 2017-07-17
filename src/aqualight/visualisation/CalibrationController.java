/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
 * FXML Controller class
 *
 * @author tsobieroy
 */
public class CalibrationController implements Initializable {

    private final String ecProgram = "";
    private final String phProgram = "";
    

    @FXML
    private TilePane tilePane;
    @FXML
    private ComboBox phDropdown;
    @FXML
    private ComboBox ecDropdown;
    
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
        
        ArrayList<String> phDropdownlist = new ArrayList<>();
        phDropdownlist.add("77 - "+PhControlController.PHControl.getLabelNamesByAddress(77));
        phDropdownlist.add("78 - "+PhControlController.PHControl.getLabelNamesByAddress(78));
        phDropdownlist.add("79 - "+PhControlController.PHControl.getLabelNamesByAddress(79));                    
        phDropdown.setItems(FXCollections.observableList(phDropdownlist));
        
        ArrayList<String> ecDropdownlist = new ArrayList<>();
        ecDropdownlist.add("73 - "+PhControlController.PHControl.getLabelNamesByAddress(73));
        ecDropdownlist.add("74 - "+PhControlController.PHControl.getLabelNamesByAddress(74));
        ecDropdown.setItems(FXCollections.observableList(ecDropdownlist));
        
        
    }

    /**
     * @brief creates a new tile for the page
     * @param label name of the label used
     * @param color color that the tile will have
     * @return stackpane as a tile
     */
    private StackPane createTile(String label, Color color)  {
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
     * @brief if an action value was clicked
     * @param event 
     */
    @FXML
    public void clickCalibration(ActionEvent event){
        String label = ((Button)event.getSource()).getId();
        event.consume();
        String probeType = "";
        String Address = "";
        String Value = "";
        
        
        if(label.equals("ph4")){
            Value = "4";
            probeType = "ph";
            Address = phDropdown.getSelectionModel().selectedItemProperty().getName();
        }
        if(label.equals("ph7")){
            Value = "7";
            probeType = "ph";
            Address = phDropdown.getSelectionModel().selectedItemProperty().getName();
        }
        if(label.equals("ph9")){
            Value = "9";
            probeType = "ph";
            Address = phDropdown.getSelectionModel().selectedItemProperty().getName();
        }
        if(label.equals("ecLow")){
            Value = "76";
            probeType = "ec";
            Address = ecDropdown.getSelectionModel().selectedItemProperty().getName();
        }
        if(label.equals("ecHigh")){
            Value = "1278";
            probeType = "ec";
            Address = ecDropdown.getSelectionModel().selectedItemProperty().getName();
        }
        if(Address != null){
        
            if(probeType.equals("ph") && !Address.equals("")){
                executeCalibration(phProgram, Address, Value);
            }
            if(probeType.equals("ec") && !Address.equals("")){
                executeCalibration(ecProgram, Address, Value);           
            }
        }                        
    }
    
    
    /**
     * @param pathToProgram addresses the path to the program e.g. "/aqualight-phcontroller-calibrator"
     * @brief execute c program from java (not tested yet)
     * @param Address i2c address of the probe
     * @param Value which ph / ec  or whatever is addressed
     * @return 
     */
    public boolean executeCalibration(String pathToProgram, String Address, String Value) {
        try {
            Process process = new ProcessBuilder(pathToProgram, Address, Value).start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            long[] args = null;

            System.out.printf("Output of running %s is:", Arrays.toString(args));

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            return false;
        }
        return false;
    }
}
