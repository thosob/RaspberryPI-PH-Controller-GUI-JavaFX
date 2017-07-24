/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.dataprocessing;

import aqualight.databastraction.ECProbe;
import aqualight.databastraction.GlobalObjects;
import aqualight.databastraction.IProbe;
import aqualight.databastraction.PhProbe;
import aqualight.databastraction.Probes;
import aqualight.databastraction.TemperatureProbe;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @brief Calls our program measures all probes and write the data into the map.
 * Should be accessed through observers, such that data can be drawn from
 * @author Thomas Sobieroy
 */
public class ControlValueDispatcher extends Observable implements Runnable {

    private HashMap<String, String> ProbeDataMap = new HashMap();

    @Override
    public void run() {

        Process process;
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        String line;
        String result;
        double value;

        //Get Probes
        Probes probes = GlobalObjects.getProbes();

        //run until eternity
        while (true) {
            ProbeDataMap.clear();

            for (IProbe probe : probes.getProbes()) {

                //get probevalue and display that to the screen
                value = probe.getLastValue().getProbeValue();

                //Check if ph probe
                if (probe.getClass().equals(PhProbe.class)) {
                    try {
                        PhProbe phprobe = (PhProbe)probe;
                        //Invoke scanning of ph
                        // This is the correct one:
                        process = new ProcessBuilder(GlobalObjects.getPhProgram(), phprobe.getTemperaturePath(), probe.getAddress(), String.valueOf(phprobe.getPh4()), String.valueOf(phprobe.getPh7()), String.valueOf(phprobe.getPh9())).start();
                        //this is for mockup testing:
                        //process = new ProcessBuilder("/aqualight-phcontroller-gui-mockup","-p").start();
                        //read it to the input stream
                        is = process.getInputStream();
                        //make it to an input stream reader
                        isr = new InputStreamReader(is);
                        //Use buffered reader to have linewise reading
                        br = new BufferedReader(isr);
                        //Go through output line by line
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            if (line.contains("Ph")) {
                                //here we need to find parse the ph value
                                result = line.replaceAll("\\D{5}$", "");
                                result = result.replaceAll("^\\D{4}", "");
                                //present read data
                                ProbeDataMap.put(probe.getAddress(), result);
                                //stop parsing
                                break;
                            }
                        }
                        //clean up
                        br.close();
                        is.close();
                        isr.close();
                        //Wait a sec
                    } catch (IOException ex) {
                        Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (probe.getClass().equals(TemperatureProbe.class)) {
                    try {
                        TemperatureProbe tempProbe = (TemperatureProbe) probe;
                        //Invoke scanning of Temperatuer
                        // This is the correct one:
                        process = new ProcessBuilder(GlobalObjects.getTemperature(), tempProbe.getPath() ).start();
                        //this is for mockup testing:
                        //process = new ProcessBuilder("/aqualight-phcontroller-gui-mockup", "-t").start();
                        //read it to the input stream
                        is = process.getInputStream();
                        //make it to an input stream reader
                        isr = new InputStreamReader(is);
                        //Use buffered reader to have linewise reading
                        br = new BufferedReader(isr);
                        //Go through output line by line
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            if (line.contains("Temperature")) {
                                //here we need to find parse the ph value
                                result = line.replaceAll("\\D{14}$", "");
                                result = result.replaceAll("^\\D{13}", "");
                                //present read data
                                ProbeDataMap.put(probe.getAddress(), result);
                                //stop parsing
                                break;
                            }
                        }
                        //clean up
                        br.close();
                        is.close();
                        isr.close();
                        //Wait a sec
                    } catch (IOException ex) {
                        Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (probe.getClass().equals(ECProbe.class)) {
                    try {
                        ECProbe ecprobe = (ECProbe)probe;                      
                        //Invoke scanning of ec                        
                        process = new ProcessBuilder(GlobalObjects.getEcProgram(), ecprobe.getTemperaturePath() ,probe.getAddress(), String.valueOf(ecprobe.getLowCalibration()), String.valueOf(ecprobe.getHighCalibration())).start();
                        //read it to the input stream
                        is = process.getInputStream();
                        //make it to an input stream reader
                        isr = new InputStreamReader(is);
                        //Use buffered reader to have linewise reading
                        br = new BufferedReader(isr);
                        //Go through output line by line
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            if (line.contains("EC")) {
                                //here we need to find parse the ph value
                                result = line.replaceAll("\\D{5}$", "");
                                result = result.replaceAll("^\\D{4}", "");
                                //present read data
                                ProbeDataMap.put(probe.getAddress(), result);
                                //stop parsing
                                break;
                            }
                        }
                        //clean up
                        br.close();
                        is.close();
                        isr.close();
                        //Wait a sec
                    } catch (IOException ex) {
                        Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                

            }
            setChanged();
            //inform all that our object has changed
            notifyObservers(ProbeDataMap);
            try {
                sleep(3000);
            } catch ( InterruptedException ex) {
                Logger.getLogger(ControlValueDispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
