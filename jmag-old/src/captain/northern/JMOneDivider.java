/*
 * JMOneDivider.java
 *
 * Created on 29 Èþëü 2006 ã., 18:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.util.Vector;

/**
 *
 * @author leo
 */
public class JMOneDivider implements JIVecDivider {
    
    /** Creates a new instance of JMOneDivider */
    public JMOneDivider() {
    }

    public Vector<Vector<JMFile>> divide(Vector<JMFile> files) {
        Vector<Vector<JMFile>> vvec = new Vector<Vector<JMFile>>();
        Vector<JMFile> rvec = new Vector<JMFile>();
        
        for(int i=0;i<files.size();i++)
        {
            JMFile file=files.get(i);
            if(file.isEnabled())
                rvec.addElement(file);
        }
        vvec.addElement(rvec);
        return vvec;
    }

}
