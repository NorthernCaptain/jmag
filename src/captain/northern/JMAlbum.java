/*
 * JMAlbum.java
 *
 * Created on 28 Èþëü 2006 ã., 9:35
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
public class JMAlbum extends JMStore {
    
    /** Creates a new instance of JMAlbum */
    public JMAlbum() {
    }

    public JMAlbum(JMOptions options) {
        super(options);
    }


    public JMAlbum(Vector<JMFile> fvec, JMOptions options) {
        super(options);
        files = fvec;
        calculateParams();
    }

}
