/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificaciondepizzas.classification.topping;

import java.util.Hashtable;

/**
 *
 * @author Alejandro
 */
public class ToppingAcceptableOverwipe {

     private Hashtable valorMap;
        public ToppingAcceptableOverwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",168.);
            valorMap.put("STDH",83.);
            valorMap.put("MS",168.);
            valorMap.put("STDS",81.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
