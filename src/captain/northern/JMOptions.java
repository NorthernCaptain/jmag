/*
 * JMOptions.java
 *
 * Created on 28 ���� 2006 �., 9:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;
import javax.swing.JTextArea;

/**
 *
 * @author Leo
 */
public class JMOptions
{
    
    /** Creates a new instance of JMOptions */
    public JMOptions()
    {
        loadOptions();
    }
    
    private int categoryThreshold[][] =
    {
        {0,  -1},
        {1,   0},
        {2,   1},
        {3,   3},
        {4,   6},
        {5,   -100} //last resort
    };
    
    int  getCategory(int value)
    {
        int i;
        for(i=0;i<5;i++)
            if(value <= categoryThreshold[i][1])
                return categoryThreshold[i][0];
        return categoryThreshold[i-1][0];
    };
    
    int getMaxCategory()
    { return categoryThreshold[5][0];};
    
    private Color categoryColors[] =
    {
        new Color(255,160,140),
        new Color(200,255,200),
        new Color(80,200,80),
        new Color(255,255,200),
        new Color(255,200,0),
        new Color(255,255,255)
    };
    
    Color getCategoryColor(int category)
    { return categoryColors[category];};
    
    JIVecDivider  getCategoryDivider()
    {
        return isDivideSubList()==true
                ? new JMCategoryDivider(this) : new JMOneDivider();
    };
    JIVecShuffler getShuffler()
    { return new JMRandomShuffler(); };
    JIVecPacker   getPacker()
    { return new JMSizePacker(this);};
    JIAlbumProcessor getAlbumProcessor()
    { return new JMProcessor(this);};
    JIFileNumerator getFileNumerator()
    { return new JMFileNumerator4();};
    JICharConverter getCharConverter()
    { return new JMRUConverter();};
    
    String getSizeString(long siz)
    {
        String res="";
        
        long gigs = siz / (1024l*1024l*1024l);
        long megs = (siz - gigs * 1024l*1024l*1024l)/(1024l*1024l);
        long kils = (siz - gigs * 1024l*1024l*1024l - megs*1024l*1024l)/1024l;
        
        if(gigs > 0)
        {
            res = Long.toString(gigs) + "." + Long.toString(megs*100l/1024l) + "Gb";
            return res;
        }
        if(megs > 0)
        {
            res = Long.toString(megs) + "." + Long.toString(kils*100l/1024l) + "Mb";
            return res;
        }
        res = Long.toString(kils) + "Kb";
        return res;
    }
    
    File              destFolder = new File("");
    File              getDestFolder()
    { return destFolder;};
    void              setDestFolder(File df)
    { destFolder=df;saveOptions();};
    
    long              destSizes[] =
    {
        512l*1024l*1024l,
        1024l*1024l*1024l,
        1536l*1024l*1024l,
        2048l*1024l*1024l,
        3076l*1024l*1024l,
        4096l*1024l*1024l,
        8192l*1024l*1024l,
        16384l*1024l*1024l,
        639l*1024l*1024l,
        700l*1024l*1024l,
        4440l*1024l*1024l
    };
    
    private long      currentDestSize = destSizes[0];
    void              setDestSizeByIdx(int idx)
    { currentDestSize=destSizes[idx];};
    long              getMaxAlbumSize()
    { return currentDestSize;};
    
    
    private boolean   resultShuffle=true;
    boolean           isResultShuffle()
    { return resultShuffle;};
    void              setResultShuffle(boolean res)
    { resultShuffle=res;saveOptions();};
    
    private boolean   subListShuffle=true;
    boolean           isSubListShuffle()
    { return subListShuffle;};
    void              setSubListShuffle(boolean res)
    { subListShuffle=res;saveOptions();};
    
    private boolean   divideSubList=true;
    boolean           isDivideSubList()
    { return divideSubList;};
    void              setDivideSubList(boolean res)
    { divideSubList=res;saveOptions();};
    
    private boolean   convertNames=true;
    boolean           isConvertNames()
    { return convertNames;};
    void              setConvertNames(boolean val)
    { convertNames = val; saveOptions();};
    
    private boolean   convertID3=true;
    boolean           isConvertID3()
    { return convertID3;};
    void              setConvertID3(boolean val)
    { convertID3 = val; saveOptions();};
    
    private boolean   saveToID3=true;
    boolean           isSaveToID3()
    { return saveToID3;};
    void              setSaveToID3(boolean val)
    { saveToID3 = val; saveOptions();};
    
    private boolean   filterMP3=true;
    boolean           isFilterMP3()
    { return filterMP3;};
    void              setFilterMP3(boolean val)
    { filterMP3 = val; saveOptions();};
    
    private boolean   readID3=true;
    boolean           isReadID3()
    { return readID3;};
    void              setReadID3(boolean val)
    { readID3 = val; saveOptions();};
    
    private int       albumCount = 0;
    int               getAlbumCount()
    { return albumCount;};
    void              nextAlbumCount()
    { albumCount++; saveOptions();};
    
    private  File     autoFolder = new File(""); //path to auto save lists
    File              getAutoFolder()
    { return autoFolder;};
    
    private  File     lastSrcListFile = new File("autosrclist.jmag");
    File              getLastSrcListFile()
    { return lastSrcListFile;};
    void              setLastSrcListFile(File from)
    { lastSrcListFile=from;addRecentFileName(from.getAbsolutePath());saveOptions();};
    
    private String    albumPrefix = "JMAG-";
    String            getAlbimPrefix()
    { return albumPrefix;};
    void              setAlbumPrefix(String pref)
    { albumPrefix = pref;saveOptions();};
    String            getAlbumName()
    { return albumPrefix + Integer.toString(albumCount);};
    
    String[]          recentFiles = {"", "", "", "", ""};
    
    String            getRecentFileName(int idx) { return recentFiles[idx];}
    void              addRecentFileName(String newName)
    {
        for(int i=0;i<recentFiles.length;i++)
            if(recentFiles[i].equals(newName))
                return;
                
        for(int i=recentFiles.length-1;i>0;i--)
            recentFiles[i]=recentFiles[i-1];
        recentFiles[0]=newName;
        saveOptions();
    }
    
    void              loadOptions()
    {
        Preferences pref = Preferences.userNodeForPackage(JMOptions.class);
        resultShuffle = pref.getBoolean("resultShuffle", true);
        subListShuffle = pref.getBoolean("subListShuffle", true);
        divideSubList = pref.getBoolean("divideSubList", true);
        convertNames = pref.getBoolean("convertNames", true);
        convertID3 = pref.getBoolean("convertID3", true);
        saveToID3 = pref.getBoolean("saveToID3", true);
        filterMP3 = pref.getBoolean("filterMP3", true);
        readID3 = pref.getBoolean("readID3", true);
        destFolder = new File(pref.get("destPath", ""));
        autoFolder = new File(pref.get("autoPath", ""));
        lastSrcListFile = new File(pref.get("lastSrcListFile", "autosrclist.mag"));
        albumCount = pref.getInt("albumCount", 1);
        albumPrefix = pref.get("albumPrefix", albumPrefix);
        for(int i=0;i<recentFiles.length;i++)
            recentFiles[i]=pref.get("recentFile" + Integer.toString(i), "");
    }
    
    void               saveOptions()
    {
        Preferences pref = Preferences.userNodeForPackage(JMOptions.class);
        pref.putBoolean("resultShuffle", resultShuffle);
        pref.putBoolean("subListShuffle", subListShuffle);
        pref.putBoolean("divideSubList", divideSubList);
        pref.putBoolean("convertNames", convertNames);
        pref.putBoolean("convertID3", convertID3);
        pref.putBoolean("saveToID3", saveToID3);
        pref.putBoolean("readID3", readID3);
        pref.putBoolean("filterMP3", filterMP3);
        pref.put("destPath", getDestFolder().getAbsolutePath());
        pref.put("autoPath", getAutoFolder().getAbsolutePath());
        pref.put("lastSrcListFile", getLastSrcListFile().getAbsolutePath());
        pref.putInt("albumCount", albumCount);
        pref.put("albumPrefix", albumPrefix);
        for(int i=0;i<recentFiles.length;i++)
            pref.put("recentFile" + Integer.toString(i), recentFiles[i]);
    }
    
    private static final JMOptions gOpt = new JMOptions();
    
    public static JMOptions getOpt()
    {
        return gOpt;
    };
    
    
    private            JTextArea dbgOut = null;
    
    void               setDbgOut(JTextArea out)
    { dbgOut = out;}
    
    void               dbg(final String str)
    {
        if(dbgOut != null)
        {
            java.awt.EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    String s = new SimpleDateFormat("HH:mm:ss: ").format(new Date());
                    dbgOut.append(s + str + "\n");
                }
            });
        }
        else
            System.out.println(str);
    }
    
    void               err(final String str)
    {
        if(dbgOut != null)
        {
            java.awt.EventQueue.invokeLater(new Runnable()
            {
                public void run()
                {
                    String s = new SimpleDateFormat("HH:mm:ss: ").format(new Date());
                    dbgOut.append("\n" + s + str + "\n");
                }
            });
        }
        else
            System.err.println(str);
    }
    
    static String    trimFileName(String filename)
    {
        String filter = "0123456789 -=:?+_!@#$%^&*|.";
        
        int i;
        for(i=0;i<filename.length()-3;i++)
            if(filter.indexOf(filename.codePointAt(i))==-1)
                break;
        return filename.substring(i);
    }
    
}
