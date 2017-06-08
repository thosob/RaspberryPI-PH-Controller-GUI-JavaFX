/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aqualight.databastraction;

/**
 * @brief has a list of global objects, that are used over multiple fxml pages
 * @author Thomas Sobieroy
 */
public class GlobalObjects {
    
    private static Probes Probes;
    
    
    public static Probes getProbes(){        
        if(Probes == null){
            Probes = new Probes();
            //Assign probe data to the probes
            ReadProbeData readProbeData = new ReadProbeData();
            readProbeData.run();
        }
        
        return Probes;        
    }
    
   
    
    
}
