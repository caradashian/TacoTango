/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tacotango;

import environment.ApplicationStarter;
import java.awt.Dimension;

/**
 *
 * @author carateresa
 */
public class TacoTango {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ApplicationStarter.run(args, "Taco Tango 4 lyfe", new Dimension(900, 725
       ), new TacoTangoEnvironment());
        
    }
    
}
