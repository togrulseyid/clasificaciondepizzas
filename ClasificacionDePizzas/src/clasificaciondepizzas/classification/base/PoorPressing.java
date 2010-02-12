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
 public class PoorPressing {

    private Hashtable valorMap;
        public PoorPressing(){

            valorMap =new Hashtable();

            valorMap.put("R",3.56193283807882);
            valorMap.put("E",0.5316015959350955);
            valorMap.put("A1",0.8257241906104942);
            valorMap.put("A2",1.1806451612903226);

        }
        public Hashtable getHash(){
            return valorMap;
        }
    }
