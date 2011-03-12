/*
 * JIAlbumProcessor.java
 *
 * Created on 28 Èþëü 2006 ã., 11:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.io.File;

/**
 *
 * @author Leo
 */
public interface JIAlbumProcessor {
    
    //Generates new album by a given storage and by defined algorithm
    JMAlbum generateAlbum(JMStore store);    

    //Shuffle all files in the album list
    void    shuffleAlbum(JMStore store);
    
    //Copy all files in the album list to the given destination folder
    void    copyAlbumFiles(JMagUI ui, JMStore album, JMStore source, File destFolder, JIProgressMeter meter);
    
}
