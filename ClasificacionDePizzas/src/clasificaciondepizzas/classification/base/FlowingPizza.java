/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificaciondepizzas.classification.base;

import java.util.Hashtable;

/**
 *
 * @author Alejandro
 */
public class FlowingPizza {

   private Hashtable valorMap;
        public FlowingPizza(){

            valorMap =new Hashtable();

            valorMap.put("R",3.520631753091136);
            valorMap.put("E",0.4801379713475091);
            valorMap.put("A1",0.7880528482698073);
            valorMap.put("A2",1.14);
        }

        public Hashtable getHash(){
            return valorMap;
        }
    }
