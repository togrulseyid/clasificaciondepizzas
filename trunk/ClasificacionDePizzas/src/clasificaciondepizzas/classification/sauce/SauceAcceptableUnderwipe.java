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
public class SauceAcceptableUnderwipe {

     private Hashtable valorMap;
        public SauceAcceptableUnderwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",10.);
            valorMap.put("STDH",21.);
            valorMap.put("MS",10.);
            valorMap.put("STDS",80.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
