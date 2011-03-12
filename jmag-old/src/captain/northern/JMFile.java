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
import org.farng.mp3.id3.ID3v1;

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
        enabled = file.exists();
        if(file.length()<512)
            enabled=false;
        if(JMOptions.getOpt().isReadID3())
            initTag();
    }
    
    public JMFile(FileInfo fi)
    {
        file = new File(fi.path);
        enabled = file.exists();
        if(file.length()<512)
            enabled=false;
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
        tag = null;
        try {
            mfl = new MP3File(file,false);
            if(mfl.hasID3v1Tag()) {
//                JMOptions.getOpt().dbg("initTag: has ID3 v1");
                tag = mfl.getID3v1Tag();
                parseInfoString(tag.getSongComment());
            } else
                if(mfl.hasID3v2Tag()) {
                tag = mfl.getID3v2Tag();
//                JMOptions.getOpt().dbg("initTag: has ID3 v2: " + tag.toString() );
                parseInfoString(tag.getSongComment());
                }
        } catch (TagException ex) {
            JMOptions.getOpt().err("initTag tag exception: " + ex);
        } catch (IOException ex) {
            JMOptions.getOpt().err("initTag io exception: " + ex);
        }
        catch (Throwable tex)
        {
            JMOptions.getOpt().err("initTag UNK exception: " + tex);
        }
    }
    
    String          getDescription() { 
        if(tag!=null)
            return tag.getLeadArtist() + ": " 
                    + tag.getAlbumTitle()+ " - " 
                    + tag.getSongTitle()+ " / "
                    + tag.getSongComment();
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
            return new ID3v1(tag);
        } else
        {
            //We don't have ID3 tag, so let's try to generate one
            //assuming that out files placed in artist/album/song.mp3
            //directory structure
            
            String tmp = file.getName();
            song = JMOptions.trimFileName(tmp.substring(0, tmp.length()-4).replaceAll("_"," "));
            album = JMOptions.trimFileName(file.getParentFile().getName().replaceAll("_"," "));
            artist = JMOptions.trimFileName(file.getParentFile().getParentFile().getName().replaceAll("_"," ")); 
            AbstractID3 ntag = new ID3v1();
            ntag.setAlbumTitle(album);
            ntag.setLeadArtist(artist);
            ntag.setSongTitle(song);
            ntag.setSongComment(getInfoString());
            return ntag;
        }
    }

    AbstractID3 getNewID3()
    {
        String song = "";
        String artist = "";
        String album = "";
        
        if(tag != null)
        {
            ID3v1 ntag = new ID3v1();
            ntag.setSongTitle(tag.getSongTitle());
            ntag.setAlbumTitle(tag.getAlbumTitle());
            ntag.setLeadArtist(tag.getLeadArtist());
            ntag.setSongGenre(tag.getSongGenre());
            ntag.setSongComment(getInfoString());
            return ntag;
        } else
        {
            //We don't have ID3 tag, so let's try to generate one
            //assuming that out files placed in artist/album/song.mp3
            //directory structure
            
            String tmp = file.getName();
            song = tmp.substring(0, tmp.length()-4).replaceAll("_"," ");
            album = file.getParentFile().getName().replaceAll("_"," ");
            artist = file.getParentFile().getParentFile().getName().replaceAll("_"," "); 
            AbstractID3 ntag = new ID3v1();
            ntag.setAlbumTitle(album);
            ntag.setLeadArtist(artist);
            ntag.setSongTitle(song);
            ntag.setSongComment(getInfoString());
            return ntag;
        }
    }
    
    /**
     * truncate a string if it longer than the argument
     *
     * @param str String to truncate
     * @param len maximum desired length of new string
     */
    public static String truncate(final String str, final int len) {
        if (str == null) {
            throw new NullPointerException("String is null");
        }
        if (len < 0) {
            throw new IndexOutOfBoundsException("Length is less than zero");
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str.trim();
    }

    public boolean seekTag(final RandomAccessFile file) throws IOException {
        final byte[] buffer = new byte[3];

        // If there's a tag, it's 127 bytes long and we'll find the tag
        file.seek(file.length() - 128);

        // read the TAG value
        file.read(buffer, 0, 3);
        final String tag = new String(buffer, 0, 3);
        return tag.equals("TAG");
    }

    
    public void writeTag(final RandomAccessFile file) throws IOException {
        ID3v1 ntag = (ID3v1) tag;
        final byte[] buffer = new byte[128];
        int i;
        int offset = 3;
        String str;
        if (seekTag(file)) {
            file.setLength(file.length() - 128);
        }
        file.seek(file.length());
        buffer[0] = (byte) 'T';
        buffer[1] = (byte) 'A';
        buffer[2] = (byte) 'G';
        str = truncate(ntag.getTitle(), 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = truncate(ntag.getArtist(), 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = truncate(ntag.getAlbum(), 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        str = truncate(ntag.getYear(), 4);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 4;
        str = truncate(ntag.getComment(), 30);
        for (i = 0; i < str.length(); i++) {
            buffer[i + offset] = (byte) str.charAt(i);
        }
        offset += 30;
        buffer[offset] = ntag.getGenre();
        file.write(buffer);
    }
    
    
    void file2Tag(JICharConverter cnv)
    {
        tag = null;
        updateTag(cnv);
    }
    
    void updateTag(JICharConverter cnv)
    {
        if(tag == null)
            tag = getFilledID3();
        tag.setSongComment(getInfoString());
        updateTag(cnv, tag);
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
            ntag.write(rfl);
        }
        catch(Exception ex)
        {
           JMOptions.getOpt().err("updateTag exception: " + ex); 
        }
        catch (Throwable tex)
        {
            JMOptions.getOpt().err("updateTag UNK exception: " + tex);
        }
        finally
        {
            try
            {
                if(rfl != null) 
                {
                    rfl.getFD().sync();
                    rfl.close();
                }
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
            tag = getFilledID3();
            RandomAccessFile rfl = null;
            try {
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
            catch (Throwable tex)
            {
                JMOptions.getOpt().err("updateTag UNK exception: " + tex);
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
