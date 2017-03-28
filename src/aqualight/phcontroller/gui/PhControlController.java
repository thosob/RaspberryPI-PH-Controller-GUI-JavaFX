/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.phcontroller.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    
    @FXML
    private Label label;
    
    
    @FXML
    private TilePane tilePane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tilePane.setPadding(new Insets(5,0,5,0));        
        tilePane.setVgap(4);
        tilePane.setHgap(4);
        tilePane.setPrefColumns(4);
      
        
        
        
        
        tilePane.getChildren().add(new Label());
        tilePane.getChildren().add(new Label());
        tilePane.getChildren().add(createTile("Übersicht",Color.CADETBLUE));
        tilePane.getChildren().add(createTile("Eichen", Color.DIMGREY));
        tilePane.getChildren().add(createTile("Statistik", Color.TURQUOISE));
        tilePane.getChildren().add(createTile("Einstellungen", Color.DARKBLUE));
         
   }    
   /**
    * @brief creates a new tile for the page
    * @param label name of the label used
    * @param color color that the tile will have
    * @return stackpane as a tile
    */
   private StackPane createTile(String label, Color color){
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
        
        stack.setOnTouchPressed((TouchEvent event) -> {
            System.out.println(event.toString());
            event.consume();            
        });
        stack.setOnMouseClicked((MouseEvent event) -> {
            System.out.println(event.toString());
            event.consume();                        
        });
        return stack;
   }
   /**
    * @brief defines how the click event is handled 
    * @param e Event that has been given
    */
   private void handleClickEvent(Event e){
       
       
   }
   
   
   
   
   
   
}
