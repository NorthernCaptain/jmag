/*
 * JMFile.java
 *
 * Created on 16 Èþëü 2006 ã., 20:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3;
import org.farng.mp3.id3.ID3v2_3;

/**
 *
 * @author Leo
 */
public class JMFile 
{
    private AbstractID3  tag=null;
    
    /** Creates a new instance of JMFile */
    public JMFile(File fl) {
        file = fl;
        if(JMOptions.getOpt().isReadID3())
            initTag();
    }
    
    public JMFile(FileInfo fi)
    {
        file = new File(fi.path);
        setCount(fi.count);
        if(JMOptions.getOpt().isReadID3())
            initTag();
    }
    
    File            file;    
    File            getFile() { return file;};
    
    int             totalSaves=0;
    private int     count=0;
    boolean         enabled=true;
    Date            lastUsed = new Date();
    
    void            initTag()
    {
        MP3File mfl;
        try {
            mfl = new MP3File(file,false);
            if(mfl.hasID3v2Tag()) {
                tag = mfl.getID3v2Tag();
                JMOptions.getOpt().dbg("initTag: has ID3 v2: " + tag.toString() );
                parseInfoString(tag.getSongComment());
            } else
                if(mfl.hasID3v1Tag()) {
                JMOptions.getOpt().dbg("initTag: has ID3 v1");
                tag = mfl.getID3v1Tag();
                parseInfoString(tag.getSongComment());
                }
        } catch (TagException ex) {
            JMOptions.getOpt().err("initTag tag exception: " + ex);
        } catch (IOException ex) {
            JMOptions.getOpt().err("initTag io exception: " + ex);
        }
    }
    
    String          getDescription() { 
        if(tag!=null)
            return tag.getLeadArtist() + ": " + tag.getAlbumTitle()+ " - " + tag.getSongTitle();
        return "unknown";
    }
    
    String          getInfoString() {
        String nfo = "JMAG:1:"+Integer.toString(getCount())+":";
        return nfo;
    }
    
    void            parseInfoString(String str)
    {
        String ar[] = str.split(":");
        if(ar[0].equals("JMAG"))
        {
            setCount(Integer.parseInt(ar[2]));
        }
    }

    AbstractID3 getFilledID3()
    {
        String song = "";
        String artist = "";
        String album = "";
        
        if(tag != null)
        {
            tag.setSongComment(getInfoString());
            return tag;
        } else
        {
            //We don't have ID3 tag, so let's try to generate one
            //assuming that out files placed in artist/album/song.mp3
            //directory structure
            
            String tmp = file.getName();
            song = tmp.substring(0, tmp.length()-4).replaceAll("_"," ");
            album = file.getParentFile().getName().replaceAll("_"," ");
            artist = file.getParentFile().getParentFile().getName().replaceAll("_"," "); 
            AbstractID3 ntag = new ID3v2_3();
            ntag.setAlbumTitle(album);
            ntag.setLeadArtist(artist);
            ntag.setSongTitle(song);
            ntag.setSongComment(getInfoString());
            return ntag;
        }
    }
    
    void updateTag(JICharConverter cnv, AbstractID3 ntag)
    {
        if(cnv != null)
        {
            ntag.setSongTitle(cnv.convert(ntag.getSongTitle()));
            ntag.setAlbumTitle(cnv.convert(ntag.getAlbumTitle()));
            ntag.setLeadArtist(cnv.convert(ntag.getLeadArtist()));
        }
        
        RandomAccessFile rfl = null;
        try
        {
            rfl = new RandomAccessFile(file, "rw");
            ntag.overwrite(rfl);
        }
        catch(Exception ex)
        {
           JMOptions.getOpt().err("updateTag exception: " + ex); 
        }
        finally
        {
            try
            {
                if(rfl != null) rfl.close();
            }
            catch(Exception ignore)
            {
                
            }
        }
    }
    
    boolean         isEnabled() { return enabled;};
    int             getCount()  { return count;};
    void            setCount(int cn) 
    { 
        count=cn;
        if(count > 6)
        {
            count = 0;
        }
    };
    
    /*
     * Updates usage statistic of this file and remember time of last use
     *
     */
    void            updateStat(boolean updateID3) 
    {
        if(count>=0) 
            setCount(count+1);
        lastUsed = new Date();
        if(updateID3) {
            if(tag == null)
                tag = getFilledID3();
            RandomAccessFile rfl = null;
            try {
                tag.setSongComment(getInfoString());
                rfl = new RandomAccessFile(file,"rw");
                tag.overwrite(rfl);
            } catch (IOException ex) {
               JMOptions.getOpt().err("updateStat io exception: " + ex); 
            }
            catch(TagException tex){
               JMOptions.getOpt().err("updateStat tag exception: " + tex); 
            }            
            catch(Exception nex){
               JMOptions.getOpt().err("updateStat exception: " + nex); 
            }            
            finally
            {
                try
                {
                    if(rfl != null) 
                        rfl.close();
                }
                catch(Exception ignore)
                {
                
                }
            }
        }
    }
}
