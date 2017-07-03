/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.visualisation;

import aqualight.databastraction.GlobalObjects;
import aqualight.dataprocessing.ControlValueDispatcher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
                // This is the correct one:
                //process = new ProcessBuilder("/aqualight-phcontroller", "", probe.getAddress(), "1799", "1644", "1520").start();
                //this is for mockup testing:
                process = new ProcessBuilder("/getOneWireTemperaturePath.sh").start();
                //read it to the input stream
                is = process.getInputStream();
                //make it to an input stream reader
                isr = new InputStreamReader(is);
                //Use buffered reader to have linewise reading
                br = new BufferedReader(isr);
                LinkedList<String> list = new LinkedList<String>();
                //Go through output line by line
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    if (line.contains("path")) {
                        //here we need to find parse the path value
                        result = line.replace("<path>", "");
                        result = result.replace("</path>", "");
                        //present read data
                        list.add(result);

                    }
                }
                temperatureDropDown.setItems((ObservableList) list);
                //clean up
                br.close();
                is.close();
                isr.close();
                //Wait a sec
            } catch (IOException ex) {
                Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Set up I2C DropDown
            try {
                
                Process process;
                InputStream is;
                InputStreamReader isr;
                BufferedReader br;
                String line;
                String result;
                double value;
                //Invoke scanning of ph
                // This is the correct one:
                //process = new ProcessBuilder("/aqualight-phcontroller", "", probe.getAddress(), "1799", "1644", "1520").start();
                //this is for mockup testing:
                process = new ProcessBuilder("i2cdetect", "-y", "1").start();
                //read it to the input stream
                is = process.getInputStream();
                //make it to an input stream reader
                isr = new InputStreamReader(is);
                //Use buffered reader to have linewise reading
                br = new BufferedReader(isr);
                LinkedList<String> list = new LinkedList<String>();
                //Go through output line by line
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                temperatureDropDown.setItems((ObservableList) list);
                //clean up
                br.close();
                is.close();
                isr.close();
                //Wait a sec
            } catch (IOException ex) {
                Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        String address = i2caddress.getSelectionModel().getSelectedItem().toString();
        String label = labelAssignment.getSelectionModel().getSelectedItem().toString();
        String labelString = labelText.getText();
        //AqualightPhControllerGui.setLabelsName(address, Integer.parseInt(label), labelString);

    }

    @FXML
    private void saveTempClick(ActionEvent event) {

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

}
