/*
 * JMRandomShuffler.java
 *
 * Created on 28 Èþëü 2006 ã., 10:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Leo
 */
public class JMRandomShuffler implements JIVecShuffler{
    
    /** Creates a new instance of JMRandomShuffler */
    public JMRandomShuffler() {
    }
    
    public Vector< JMFile > shuffle(Vector< JMFile > files)
    {
        Vector< JMFile > vec = new Vector< JMFile >();
        
        if(files.size()<1)
            return vec;
        
        Vector< JMFile > src = (Vector< JMFile >)files.clone();
        
        
        Random    rnd = new Random();
        Date      dat = new Date();
        
        rnd.setSeed(dat.getTime());

        while(src.size()>1)
        {
            int idx = rnd.nextInt(src.size());
            vec.addElement(src.get(idx));
            src.remove(idx);
        }
        vec.addElement(src.get(0));
        return vec;
    }    
}
