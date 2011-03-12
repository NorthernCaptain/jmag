/*
 * JMFileNumerator4.java
 *
 * Created on 28 Èþëü 2006 ã., 14:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

/**
 *
 * @author Leo
 */
public class JMFileNumerator4 implements JIFileNumerator{
    
    private int           counter = 1;
    
    /** Creates a new instance of JMFileNumerator4 */
    public JMFileNumerator4() {
    }

    public String enumerate(String filename) {
        String dig = Integer.toString(counter);
        String result = "";
        String filter = "0123456789 -=:?+_!@#$%^&*|.";
        
        int i;
        for(i = 0; i< 4-dig.length();i++)
            result += "0";
        result += dig + "-";
       
        for(i=0;i<filename.length()-5;i++)
            if(filter.indexOf(filename.codePointAt(i))==-1)
                break;
        result += filename.substring(i);
        
        counter++;
        return result;
    }
}
