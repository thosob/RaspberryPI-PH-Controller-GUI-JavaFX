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
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author tsobieroy
 */
public class CalibrationController implements Initializable {

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
