/*
 * JCopyFile.java
 *
 * Created on 28 Èþëü 2006 ã., 13:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Leo
 */
public class JCopyFile {
    
    File               fromFile = null;
    File               toFile = null; //full path to dest file including filename
    
    /** Creates a new instance of JCopyFile */
    public JCopyFile(File from, String tofile) {
        setFiles(from, tofile);
    }
    
    public JCopyFile()
    {
    }
    
    void setFiles(File from, String tofile)
    {
        fromFile = from;
        toFile = new File(tofile);
    }
    
    boolean copy(JIProgressMeter meter) {
        // Create variables for in/out streams
        FileInputStream fis = null;
        FileOutputStream fos = null;
        boolean result = true;
        
        try {
            // Create in stream
            fis = new FileInputStream(fromFile);
            
            // Create out stream
            fos = new FileOutputStream(toFile, false);
            
            // Declare variable for each byte read
            byte ar[] = new byte[1024*100];
            int siz=0;
            
            // Read byte til end of file
            while ((siz = fis.read(ar)) != -1) {
                
                
                // Put bytes read into out stream
                fos.write(ar, 0, siz);
                if(meter!=null)
                    meter.updateValue(siz);
            }
            
            // Catch FileNotFoundException
        } catch (FileNotFoundException e) {
            JMOptions.getOpt().err("File not found: " + e);
            result = false;
            // Catch IOException
        } catch (IOException e) {
            JMOptions.getOpt().err("I/O problems: " + e);
            result = false;            
            // Close files
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }
    
}
