/*
 * JIVecPacker.java
 *
 * Created on 28 ���� 2006 �., 10:42
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
public interface JIVecPacker {

    Vector < JMFile > flatten(Vector < Vector < JMFile > > vvec);
    
}
