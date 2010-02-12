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
public class Standard{

   private Hashtable valorMap;
        public Standard(){

            valorMap =new Hashtable();

            valorMap.put("R",3.9867989599043847);
            valorMap.put("E",0.1764061951243496);
            valorMap.put("A1",0.7954575576841978);
            valorMap.put("A2",1.01616161616163);

        }

        public Hashtable getHash(){
        return valorMap;
    }
    }

