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
public class SauceEvenSpread {

     private Hashtable valorMap;
        public SauceEvenSpread(){

            valorMap =new Hashtable();

            valorMap.put("MH",13.);
            valorMap.put("STDH",30.);
            valorMap.put("MS",13.);
            valorMap.put("STDS",95.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
