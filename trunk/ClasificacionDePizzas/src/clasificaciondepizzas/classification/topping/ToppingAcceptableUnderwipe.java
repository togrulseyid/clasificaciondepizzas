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
public class ToppingAcceptableUnderwipe {

     private Hashtable valorMap;
        public ToppingAcceptableUnderwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",151.);
            valorMap.put("STDH",99.);
            valorMap.put("MS",151.);
            valorMap.put("STDS",70.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
