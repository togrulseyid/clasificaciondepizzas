/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificaciondepizzas.classification.sauce;

import java.util.Hashtable;

/**
 *
 * @author Alejandro
 */
public class SauceAcceptableOverwipe {

     private Hashtable valorMap;
        public SauceAcceptableOverwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",23.);
            valorMap.put("STDH",30.);
            valorMap.put("MS",23.);
            valorMap.put("STDS",107.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
