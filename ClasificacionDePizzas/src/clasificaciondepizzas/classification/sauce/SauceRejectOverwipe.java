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
public class SauceRejectOverwipe {

     private Hashtable valorMap;
        public SauceRejectOverwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",23.);
            valorMap.put("STDH",36.);
            valorMap.put("MS",23.);
            valorMap.put("STDS",108.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
