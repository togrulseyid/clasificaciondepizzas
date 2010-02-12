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
public class SauceRejectUnderwipe {

     private Hashtable valorMap;
        public SauceRejectUnderwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",8.);
            valorMap.put("STDH",23.);
            valorMap.put("MS",8.);
            valorMap.put("STDS",75.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
