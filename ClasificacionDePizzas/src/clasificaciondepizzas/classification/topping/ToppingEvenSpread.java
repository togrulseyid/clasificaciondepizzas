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
public class ToppingEvenSpread {

     private Hashtable valorMap;
        public ToppingEvenSpread(){

            valorMap =new Hashtable();

            valorMap.put("MH",170.);
            valorMap.put("STDH",90.);
            valorMap.put("MS",170.);
            valorMap.put("STDS",68.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
