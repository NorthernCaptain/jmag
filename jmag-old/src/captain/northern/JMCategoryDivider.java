/*
 * JMCategoryDivider.java
 *
 * Created on 28 Èþëü 2006 ã., 9:56
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
public class JMCategoryDivider implements JIVecDivider {
    
    protected JMOptions opt;
    
    /** Creates a new instance of JMCategoryDivider */
    public JMCategoryDivider(JMOptions options) {
        opt = options;
    }
    
    public Vector< Vector<JMFile> > divide(Vector<JMFile> files)
    {
        Vector < Vector<JMFile> > vec = new Vector< Vector<JMFile> >();
        int i;
        for(i=0;i<opt.getMaxCategory();i++)
            vec.addElement(new Vector<JMFile>());
        
        
        for(i=0;i<files.size();i++)
        {
            JMFile file=files.elementAt(i);
            if(file.isEnabled())
            {
                int cat = opt.getCategory(file.getCount());
                Vector< JMFile > v = vec.elementAt(cat);
                v.addElement(file);
            }
        }
        
        return vec;
    }
    
}
