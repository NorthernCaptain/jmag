/*
 * JIVecShuffler.java
 *
 * Created on 28 Èþëü 2006 ã., 10:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.util.Vector;

/**
 *
 * @author Leo
 */
public interface JIVecShuffler {
    
    /*
     * Shuffle given files in the vector by defined algorithm,
     * return new shuffled vector, source vector remains unchanged
     */
    Vector< JMFile > shuffle(Vector< JMFile> files);
    
}
