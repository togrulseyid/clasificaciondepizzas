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
public class PoorAlignment{

   private Hashtable valorMap;
        public PoorAlignment(){

            valorMap =new Hashtable();

            valorMap.put("R",3.116506910168715);
            valorMap.put("E",0.612768878987792);
            valorMap.put("A1",0.774331256545432);
            valorMap.put("A2",1.2654028436018958);

        }

        public Hashtable getHash(){
            return valorMap;
         }
    }
