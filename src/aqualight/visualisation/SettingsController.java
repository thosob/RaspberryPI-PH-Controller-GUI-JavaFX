/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.databastraction.ECProbe;
import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.PhProbe;
import aqualight.databastraction.Probes;
import aqualight.dataprocessing.ControlValueDispatcher;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
public class SettingsController implements Initializable {

    @FXML
    public TilePane tilePane;
    @FXML
    public TextField deviceName;
    @FXML
    public TextField serverURL;
    @FXML
    public ComboBox i2caddress;
    @FXML
    public ComboBox labelAssignment;
    @FXML
    public TextField labelText;
    @FXML
    public ComboBox temperatureDropDown;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            GlobalObjects.initializeGlobalObjects();

            String device = GlobalObjects.getDeviceName();
            String name = GlobalObjects.getServerName();

            deviceName.setText(device);
            serverURL.setText(name);

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

            ArrayList<String> temperatureList = new ArrayList<>();
            //Set up Temperature Dropdown 
            try {
                Process process;
                InputStream is;
                InputStreamReader isr;
                BufferedReader br;
                String line;
                String result;
                double value;

                //Invoke scanning of ph                
                //this is for mockup testing:
                process = new ProcessBuilder("/getOneWireTemperaturePath.sh").start();
                //read it to the input stream
                is = process.getInputStream();
                //make it to an input stream reader
                isr = new InputStreamReader(is);
                //Use buffered reader to have linewise reading
                br = new BufferedReader(isr);

                //Go through output line by line
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    if (line.contains("path")) {
                        //here we need to find parse the path value
                        result = line.replace("<path>", "");
                        result = result.replace("</path>", "");
                        //present read data
                        temperatureList.add(result);

                    }
                }
                temperatureDropDown.setItems(FXCollections.observableList(temperatureList));
                //clean up
                br.close();
                is.close();
                isr.close();

                //Wait a sec
            } catch (Exception ex) {
                Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                //Dummy Values
                temperatureList.add("Test1");
                temperatureList.add("Test2");
                temperatureDropDown.setItems(FXCollections.observableList(temperatureList));
            }

            ArrayList<String> i2cList = new ArrayList<>();
            i2cList.add("72");
            i2cList.add("73");
            i2cList.add("74");
            i2cList.add("75");
            i2cList.add("76");
            i2cList.add("77");
            i2cList.add("78");
            i2cList.add("79");
            //Set up I2C DropDown
            i2caddress.setItems(FXCollections.observableList(i2cList));

        } catch (SQLException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Label 1");
        labels.add("Label 2");
        labels.add("Label 3");
        labels.add("Label 4");
        labels.add("Label 5");
        labels.add("Label 6");                    
        labels.add("Temp 1");
        labels.add("Temp 2");
        labelAssignment.setItems(FXCollections.observableList(labels));        
        
       
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
     * @brief on save server click change the names also in database
     * @param event
     */
    @FXML
    private void saveServerClick(ActionEvent event) {
        String URL = serverURL.getText();
        String device = deviceName.getText();
        if (!device.isEmpty()) {
            GlobalObjects.setDeviceName(device);
        }
        if (!URL.isEmpty()) {
            GlobalObjects.setServerName(URL);
        }

    }

    @FXML
    private void addClick(ActionEvent event) {        
        int label = getLabelInteger(labelAssignment.getSelectionModel().getSelectedItem().toString());
        String labelString = labelText.getText();
        
        
        PhControlController.PHControl.SetLabelName(label, labelString);

        
    }
    /**
     * @brief gets back the integer of the label
     * @param label
     * @return 
     */
    private int getLabelInteger(String label){
        int intAddress = 0;
        if(label.equals("Label 1")){
            intAddress = 0;
        }
        if(label.equals("Label 2")){
            intAddress = 1;
        }
        if(label.equals("Label 3")){
            intAddress = 2;
        }
        if(label.equals("Label 4")){
            intAddress = 3;
        }
        if(label.equals("Label 5")){
            intAddress = 4;
        }
        if(label.equals("Label 6")){
            intAddress = 5;
        }                
        if(label.equals("Temp 1")){
            intAddress = 6;
        }
        if(label.equals("Temp 2")){
            intAddress = 7;
        }
        return intAddress;
    }
    
    /**
     * @brief save temperature assignment click function
     * @param event 
     */
    @FXML
    private void saveTempClick(ActionEvent event) {
        String probeAddress = i2caddress.getSelectionModel().getSelectedItem().toString();
        String temperatureProbe = temperatureDropDown.getSelectionModel().getSelectedItem().toString();
        
        Probes probes =  GlobalObjects.getProbes();
        Object obj = probes.getProbe(probeAddress);
        try{
            //Change in running program
            if(obj.getClass() == PhProbe.class){
                PhProbe phProbe = (PhProbe)obj;
                phProbe.setTemperatureID(pathToTemperatureID(temperatureProbe));
                phProbe.writeChanges();
            }
            if(obj.getClass() == ECProbe.class){
                ECProbe ecProbe = (ECProbe)obj;            
                ecProbe.setTemperatureID(pathToTemperatureID(temperatureProbe));
                ecProbe.writeChanges();
            }
        }
        catch(Exception ecv){
            System.err.println(ecv);
        }
        
    }
    /**
     * @brief gets temperature id from db
     * @param path path to temperature id
     * @return integer of the id
     */
    private int pathToTemperatureID(String path) throws SQLException{
            // create a connection to the database
            Connection connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());                        
            PreparedStatement statement = connection.prepareStatement("SELECT temparatureId FROM temparatureSensor WHERE path = '"+path+"'");
            ResultSet Result = statement.executeQuery();                       
            return Result.next() ? Result.getInt(0) : 0;            
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
        } catch (Exception exc) {
            System.err.println(exc);
        }
        System.out.println("Could not determine menu title - "+menuTitle );
        return false;
    }

}
