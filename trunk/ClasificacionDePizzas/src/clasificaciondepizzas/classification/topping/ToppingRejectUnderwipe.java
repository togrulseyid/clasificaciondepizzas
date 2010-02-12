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
public class ToppingRejectUnderwipe {

     private Hashtable valorMap;
        public ToppingRejectUnderwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",171.);
            valorMap.put("STDH",93.);
            valorMap.put("MS",171.);
            valorMap.put("STDS",42.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
