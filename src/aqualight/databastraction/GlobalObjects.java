/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @brief has a list of global objects, that are used over multiple fxml pages
 *        This file is the configuration file of the controller. In production
 *        use this will have to be some kind of configuration file.
 * @author Thomas Sobieroy
 */
public class GlobalObjects {
        
    /**
     * These are the production variables. The controller consist of several 
     * small programs that can have different paths. Please keep in mind, that 
     * either the user executing or the file itself needs to have execution 
     * permission
     */
    private final static File Database = new File("/symbiofilter.db");       
    private final static String PhProgramCalib = "/aqualight-phcontroller-calibrator";
    private final static String EcProgramCalib = "/aqualight-conductivity-calibrator";
    private final static String PhProgram = "/aqualight-phcontroller";
    private final static String EcProgram = "/aqualight-conductivity";
    private final static String Temperature = "/readingtemperature";
    private final static String ImagePath = "/camera.jpg";
    private final static String CaptureImage = "raspistill";
    /**
     * Define these variables for mockup testing, they will be used if other than arm architecture 
     * is detected. Else the production variables will be used.
     */
    private final static String TestPhProgram = "/test_ph";
    private final static String TestEcProgram = "/test_ec";
    private final static String TestTemperature = "/test_temp";
    private final static String TestPhProgramCalib = "/test_tp";
    private final static String TestEcProgramCalib = "/test_tc";
    private final static String TestImagePath = "/camera.jpg";
    private final static File TestDatabase = new File("/symbiofilter.db");      
    
    
    private static Probes Probes;    
    private static String ServerName;
    private static String DeviceName;
    /**
     * @brief initializes all global objects - useful for unit-tests
     */
    public static void initializeGlobalObjects() throws SQLException{
                        
        //DeviceName
        Connection Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());        
        //Should be limited to an hours data, so the raspberry pi is not killed
        PreparedStatement statement = Connection.prepareStatement("SELECT deviceName FROM device");        
        ResultSet res = statement.executeQuery();
        String sqlDeviceName = res.getString(1);
        if(sqlDeviceName != null){
            DeviceName = sqlDeviceName;
        }
        
        //ServerName
        statement = Connection.prepareStatement("SELECT sendAddress FROM device");        
        res = statement.executeQuery();
        String sqlServerName = res.getString(1);
        if(sqlServerName != null){
            ServerName = sqlServerName;
        }
        statement.close();
        Connection.close();
        
        //Initializes all probes
        getProbes();
        
    }
    
    
    /**
     * @brief gets probe objects
     * @return 
     */
    public static Probes getProbes(){        
        if(Probes == null){
            Probes = new Probes();
            //Assign probe data to the probes
            ReadProbeData readProbeData = new ReadProbeData();
            readProbeData.run();
        }
        
        return Probes;        
    }
    /**
     * @brief gets database path
     * @return 
     */
    public static String getDatabasePath(){        
        return "jdbc:sqlite:"+Database.getAbsolutePath();
    }
    
    /**
     * @brief gets database path
     * @return 
     */
    public static File getDatabaseFile(){        
        if(System.getProperty("os.arch").contains("arm")){
            return Database;
        }
        else{
            return TestDatabase;
        }
    }

    /**
     * @return the ServerName
     */
    public static String getServerName() {
        return ServerName;
    }

    /**
     * @param aServerName the ServerName to set
     */
    public static void setServerName(String aServerName) {
        ServerName = aServerName;
        Connection Connection = null;        
        try {           
            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());

            //Should be limited to a hours data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("UPDATE device SET sendAddress = ?");
            statement.setString(1, ServerName);
            statement.executeUpdate();
            statement.close();
            Connection.close();
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
     * @return the DeviceName
     */
    public static String getDeviceName() {
        return DeviceName;
    }

    /**
     * @param aDeviceName the DeviceName to set
     */
    public static void setDeviceName(String aDeviceName) {
        DeviceName = aDeviceName;
        Connection Connection = null;        
        try {           
            // create a connection to the database
            Connection = DriverManager.getConnection(GlobalObjects.getDatabasePath());

            //Should be limited to a hours data, so the raspberry pi is not killed
            PreparedStatement statement = Connection.prepareStatement("UPDATE device SET deviceName = ?");
            statement.setString(1, DeviceName);
            statement.executeUpdate();
            statement.close();
            Connection.close();
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
     * @return the PhProgramCalib
     */
    public static String getPhProgramCalib() {
        if(System.getProperty("os.arch").contains("arm")){
            return PhProgramCalib;
        }
        else{
            return TestPhProgramCalib;
        }       
    }

    /**
     * @return the EcProgramCalib
     */
    public static String getEcProgramCalib() {
        if(System.getProperty("os.arch").contains("arm")){
            return EcProgramCalib;
        }
        else{
            return TestEcProgramCalib;
        }
    }

    /**
     * @return the PhProgram
     */
    public static String getPhProgram() {
        if(System.getProperty("os.arch").contains("arm")){
            return PhProgram;
        }
        else{
            return TestPhProgram;
        }        
    }

    /**
     * @return the EcProgram
     */
    public static String getEcProgram() {
        if(System.getProperty("os.arch").contains("arm")){
            return EcProgram;
        }
        else{
            return TestEcProgram;
        }          
    }

    /**
     * @return the Temperature
     */
    public static String getTemperature() {
        if(System.getProperty("os.arch").contains("arm")){
            return Temperature;
        }
        else{
            return TestTemperature;
        }        
    }                  

    /**
     * @return the ImagePath
     */
    public static String getImagePath() {
        if(System.getProperty("os.arch").contains("arm")){
            return ImagePath;
        }
        else{
            return TestImagePath;
        }               
    }

    /**
     * @return the CaptureImage
     */
    public static String getCaptureImage() {
        return CaptureImage;
    }
}
