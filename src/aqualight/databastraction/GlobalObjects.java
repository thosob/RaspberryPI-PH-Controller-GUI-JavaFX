/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

import java.io.File;

/**
 * @brief has a list of global objects, that are used over multiple fxml pages
 * @author Thomas Sobieroy
 */
public class GlobalObjects {
    
    private static Probes Probes;
    private final static File Database = new File("/symbiofilter.db");       
    
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
        return Database;
    }
    
   
    
    
}
