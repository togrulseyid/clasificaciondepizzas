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
public class ToppingRejectOverwipe {

     private Hashtable valorMap;
        public ToppingRejectOverwipe(){

            valorMap =new Hashtable();

            valorMap.put("MH",172.);
            valorMap.put("STDH",81.);
            valorMap.put("MS",172.);
            valorMap.put("STDS",87.);

        }

        public Hashtable getHash(){
            return valorMap;
        }
}
