/*
 * JIVecDivider.java
 *
 * Created on 28 Èþëü 2006 ã., 9:53
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
public interface JIVecDivider {

    /*
     * Divide vector of files to vectors of vectors of files by
     * special algorithm
     *
     */
    Vector< Vector<JMFile> > divide(Vector<JMFile> files);    
}
