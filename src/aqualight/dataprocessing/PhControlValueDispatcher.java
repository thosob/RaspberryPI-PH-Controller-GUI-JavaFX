/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.dataprocessing;

import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.IProbe;
import aqualight.databastraction.Probes;
import aqualight.visualisation.AqualightPhControllerGui;
import static aqualight.visualisation.AqualightPhControllerGui.GetLabel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 * @brief Contains
 * @author Thomas Sobieroy
 */
public class PhControlValueDispatcher implements Runnable {

    @Override
    public void run() {

        //Get Probes
        Probes probes = GlobalObjects.getProbes();

        for (IProbe probe : probes.getProbes()) {

            //get probevalue and display that to the screen
            double value = probe.getLastValue().getProbeValue();

            //Write data into labels
            Label label = GetLabel(probe.getAddress());
            if (label == null) {
                               
                label = GetLabel(probe.getAddress());
                Process process;
                
                try {
                    //Invoke scanning of ph
                    process = new ProcessBuilder("C:\\PathToExe\\MyExe.exe", "param1", "param2").start();
                    //read it to the input stream
                    InputStream is = process.getInputStream();                    
                    InputStreamReader isr = new InputStreamReader(is);
                    //Use buffered reader to have linewise reading
                    BufferedReader br = new BufferedReader(isr);
                    //Go through output line by line                    
                    String line; String result;
                    while ((line = br.readLine()) != null) {
                        //here we need to find parse the ph value
                        System.out.println(line);
                        result = line;
                    }                    
                    //Finally set up label
                    AqualightPhControllerGui.SetUpLabel(probe.getAddress());
                    label.setText(String.valueOf(value));
                    
                } catch (IOException ex) {
                    Logger.getLogger(PhControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                }

             

            } else {
                label.setText(String.valueOf(value));
            }
        }

    }

}
