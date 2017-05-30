/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import aqualight.probes.PhProbeData;
import aqualight.probes.ECProbeData;
import static aqualight.visualisation.AqualightPhControllerGui.GetLabel;
import static aqualight.visualisation.AqualightPhControllerGui.SetUpLabel;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javafx.scene.control.Label;

/**
 * @brief Reads runnable data
 * @author Thomas Sobieroy
 */
public class ReadProbeData implements Runnable {

    //Assembles all data in model 
    @Override
    public void run() {
        Connection Connection = null;
        String Address = "";

        try {
            // db parameters
            String Url = "jdbc:sqlite:resources/symbiofilter.db";
            // create a connection to the database
            Connection = DriverManager.getConnection(Url);

            //Should be limited to a weeks data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("SELECT address, time, ph, temperature, conductivity FROM queue");
            ResultSet Result = statement.executeQuery();
            String Date = null;
            //Go through all data and add it to the list
            while (Result.next()) {
                Address = Result.getString(1);

                //If we cannot find the address as a value, the probe was not registered, yet.
                if (!ProbeData.DataHandlerList.containsKey(Address)) {
                    //we have to initialize a new list, always using Address as ID
                    //to register the probe
                    ProbeData.DataHandlerList.put(Address, new ProbeData(Address, new LinkedList<>(), getProbeType(Result)));
                }

                //If data comes from ph probe
                if (ProbeData.DataHandlerList.get(Address).getProbeType().compareTo("ph") > -1) {

                    long date = Result.getLong(2);
                    Date realDate = new Date(date);
                    double ph = Result.getDouble(3);
                    double temperature = Result.getDouble(4);

                    //then we write the Data to a list
                    ProbeData.DataHandlerList.get(Address).Values.add(new PhProbeData(realDate, temperature, ph));
                } else {
                    //Data comes from EC-Probe
                    long date = Result.getLong(2);
                    Date realDate = new Date(date);
                    double ec = Result.getDouble(3);
                    double temperature = Result.getDouble(4);
                    //This should be for ECProbe
                    ProbeData.DataHandlerList.get(Address).Values.add(new ECProbeData(realDate, temperature, ec));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (Connection != null) {
                    //And close connection, to free up space
                    Connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * @brief determines by a result set the type of probe
     * @param Result ResultSet with data
     * @return e.g. "ph", "conductivity" or sth. else
     */
    public static String getProbeType(ResultSet Result) {
        //Distinction is missing here
        return "ph";
    }
}
