/*
 * JMSizePacker.java
 *
 * Created on 28 Èþëü 2006 ã., 10:43
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
public class JMSizePacker implements JIVecPacker {
    
    private JMOptions opt;
    /** Creates a new instance of JMSizePacker */
    public JMSizePacker(JMOptions options) {
        opt = options;
    }
    
    public Vector<JMFile> flatten(Vector<Vector<JMFile>> vvec) {
        Vector<JMFile> files = new Vector<JMFile>();
        
        long size_limit=opt.getMaxAlbumSize();
        
        long siz = 0;
        for(int i = 0; i<vvec.size(); i++)
        {
            Vector<JMFile> cur = vvec.get(i);
            for(int j=0;j<cur.size();j++)
            {
                JMFile file = cur.get(j);
                if(siz + file.getFile().length() > size_limit)
                    return files;
                files.addElement(file);
                siz += file.getFile().length();
            }
        }
        
        return files;
    }    
}
