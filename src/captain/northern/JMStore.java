/*
 * JMStore.java
 *
 * Created on 16 Èþëü 2006 ã., 20:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import org.farng.mp3.id3.AbstractID3;

/**
 *
 * @author Leo
 */

class FileInfo implements Serializable
{
    String               path;
    int                  count;
    FileInfo(JMFile from)
    {
        path = from.getFile().getAbsolutePath();
        count = from.getCount();
    }
};


public class JMStore
{
    
    protected JMOptions         opt;
    
    protected Vector<JMFile>  files = new Vector<JMFile>();
    protected File            saveFile;
    
    class JMFInfo
    {
        int                   nfiles = 0;
        long                  size = 0L;
        
        JMFInfo()
        {
            clear();
        }
        void                  clear()
        {
            nfiles = 0;
            size = 0L;
        };
    };
    
    protected JMFInfo[]        categoryInfo = null;
    
    /** Creates a new instance of JMStore */
    public JMStore()
    {
    }
    
    public JMStore(JMOptions options)
    {
        opt = options;
        saveFile = opt.getLastSrcListFile();
        categoryInfo = new JMFInfo[opt.getMaxCategory()+1];
        for(int i=0;i<opt.getMaxCategory()+1;i++)
            categoryInfo[i]=new JMFInfo();
    }
    
    
    void setSaveFile(File file)
    {
        saveFile = file;
    }
    
    void cleanAll()
    {
        files = new Vector<JMFile>();
        calculateParams();
    }
    
    void addFiles(File[] flist)
    {
        for(int i=0;i<flist.length;i++)
        {
            if(flist[i].isFile())
            {
                if(opt.isFilterMP3())
                {
                    if(!flist[i].getName().toLowerCase().endsWith(".mp3"))
                        continue;
                }
                files.add(new JMFile(flist[i]));
            }
            else
            {
                if(flist[i].isDirectory())
                    addFiles(flist[i].listFiles());
            }
        }
        calculateParams();
    }
    
    Vector<JMFile> getFiles()
    { return files; }
    
    void           setFiles(Vector<JMFile> fvec)
    { files = fvec;};
    
    class JMTableModel extends AbstractTableModel
    {
        private String[] columnNames =  {
            "Used", "Filename", "Directory",  "Size", "Time", "Description", "Exists"
        };
        
        public int getColumnCount()
        {
            return columnNames.length;
        }
        
        public int getRowCount()
        {
            return files.size();
        }
        
        public String getColumnName(int col)
        {
            return columnNames[col];
        }
        
        public Object getValueAt(int row, int col)
        {
            switch(col)
            {
                case 0:
                    return new Integer(files.get(row).getCount());
                case 1:
                    return files.get(row).getFile().getName();
                case 3:
                    return opt.getSizeString(files.get(row).getFile().length());
                case 4:
                    return (new SimpleDateFormat("dd.MM.yyyy HH:mm")).format(new Date(files.get(row).getFile().lastModified()));
                case 2:
                    return files.get(row).getFile().getParentFile().getName();
                case 5:
                    return files.get(row).getDescription();
                case 6:
                    return files.get(row).isEnabled();
                default:
                    return null;
            }
        }
        
        
    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
        public boolean isCellEditable(int row, int col)
        {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return false;
        }
        
    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
        public void setValueAt(Object value, int row, int col)
        {
            fireTableCellUpdated(row, col);
        }
    }
    
    AbstractTableModel getTableModel()
    { return new JMTableModel(); }
    
    void removeFiles(int [] rows)
    {
        Vector<JMFile>  nfiles = new Vector<JMFile>();
        for(int i=0;i<files.size();i++)
        {
            boolean skip = false;
            for(int j=0;j<rows.length;j++)
                if(rows[j]==i)
                {
                skip=true;
                break;
                }
            if(!skip)
                nfiles.addElement(files.get(i));
        }
        files = nfiles;
        calculateParams();
    }
    
    void setCountFiles(int [] rows, int newCount)
    {
        for(int i=0;i<rows.length;i++)
            files.get(rows[i]).setCount(newCount);
        calculateParams();
    }
    
    void addCountFiles(int [] rows, int deltaCount)
    {
        for(int i=0;i<rows.length;i++)
            files.get(rows[i]).setCount(files.get(rows[i]).getCount() + deltaCount);
        calculateParams();
    }
    
    void randomizeCountFiles(int [] rows)
    {
        Random    rnd = new Random();
        Date      dat = new Date();
        
        rnd.setSeed(dat.getTime());
        
        for(int i=0;i<rows.length;i++)
            files.get(rows[i]).setCount(rnd.nextInt(7));
        calculateParams();
    }
    
    void readID3Files(int [] rows, JIProgressMeter meter)
    {
        for(int i=0;i<rows.length;i++)
        {
            files.get(rows[i]).initTag();
            if(meter != null)
            {
                meter.setNote("[" + Integer.toString(i+1) + " of " + Integer.toString(rows.length)
                + "]: " + files.get(rows[i]).getFile().getName());
                meter.updateValue(1);
                if(meter.isCancelled())
                    break;
            }
        }
        calculateParams();
    }
    
    void updateID3Files(int [] rows, JIProgressMeter meter)
    {
        JICharConverter cnv = opt.getCharConverter();
        for(int i=0;i<rows.length;i++)
        {
            JMFile file = files.get(rows[i]);
            file.updateTag(cnv);
            if(meter != null)
            {
                meter.setNote("[" + Integer.toString(i+1) + " of " + Integer.toString(rows.length)
                + "]: " + file.getFile().getName());
                meter.updateValue(1);
                if(meter.isCancelled())
                    break;
            }
        }
        calculateParams();
    }
    
    void file2ID3Files(int [] rows, JIProgressMeter meter)
    {
        JICharConverter cnv = opt.getCharConverter();
        for(int i=0;i<rows.length;i++)
        {
            JMFile file = files.get(rows[i]);
            file.file2Tag(cnv);
            if(meter != null)
            {
                meter.setNote("[" + Integer.toString(i+1) + " of " + Integer.toString(rows.length)
                + "]: " + file.getFile().getName());
                meter.updateValue(1);
                if(meter.isCancelled())
                    break;
            }
        }
        calculateParams();
    }
    
    void ID3toFiles(String format, int [] rows, JIProgressMeter meter)
    {
        JICharConverter cnv = opt.getCharConverter();
        for(int i=0;i<rows.length;i++)
        {
            JMFile file = files.get(rows[i]);
            String fname = file.genNameFromID3(format,i+1);
            opt.dbg("New file name from ID3: " + fname);
            File rfile = file.getFile();
            File nfile = new File(rfile.getParentFile().getAbsolutePath() + rfile.separator + fname);
            if(!rfile.renameTo(nfile))
                JMOptions.getOpt().err("Error renaming file: " + rfile.getAbsolutePath() + " -> " +
                        nfile.getAbsolutePath());
            else
                file.setFile(nfile);
            
            if(meter != null)
            {
                meter.setNote("[" + Integer.toString(i+1) + " of " + Integer.toString(rows.length)
                + "]: " + file.getFile().getName());
                meter.updateValue(1);
                if(meter.isCancelled())
                    break;
            }
        }
        calculateParams();
    }
    
    
    protected long  totalSize = 0;
    long            getTotalSize()
    { return totalSize;};
    int             getNumFiles()
    { return files.size();};
    JMFInfo         getCategoryInfo(int category)
    { return categoryInfo[category];};
    
    void            calculateParams()
    {
        for(int j = 0;j<opt.getMaxCategory()+1;j++)
            categoryInfo[j].clear();
        
        totalSize = 0;
        for(int i=0;i<files.size();i++)
        {
            JMFile file = files.get(i);
            if(!file.isEnabled())
                continue;
            totalSize += file.getFile().length();
            JMFInfo inf = categoryInfo[opt.getCategory(file.getCount())];
            inf.nfiles++;
            inf.size+=file.getFile().length();
        }
    }
    
    boolean           load(File fromfile, JIProgressMeter meter)
    {
        int i = 1;
        boolean ret = false;
        try
        {
            FileInputStream fstr = null;
            fstr = new FileInputStream(fromfile);
            ObjectInputStream ostr = new ObjectInputStream(fstr);
            
            try
            {
                Integer siz = (Integer)ostr.readObject();
                if(meter != null)
                    meter.setMaximum(siz.longValue());
                files = new Vector<JMFile>();
                ret = true;
                while(true)
                {
                    
                    FileInfo fi = (FileInfo)ostr.readObject();
                    files.addElement(new JMFile(fi));
                    if(meter != null)
                    {
                        meter.setNote("[" + Integer.toString(i++) + " of " + siz
                                + "]: " + fi.path);
                        meter.updateValue(1);
                    }
                    opt.dbg("Read file info: " + fi.path + " count:" + Integer.toString(fi.count));
                }
            }
            catch(Exception ee)
            {
                opt.err("Read exception: " + ee);
            }
            finally
            {
                ostr.close();
            }
        }
        catch(IOException ioex)
        {
            opt.err("Load file io exception: " + ioex);
        }
        catch(Exception ex)
        {
            opt.err("Load file exception: " + ex);
        }
        calculateParams();
        return ret;
    }
    
    void           save(File tofile)
    {
        try
        {
            FileOutputStream fstr = null;
            fstr = new FileOutputStream(tofile);
            ObjectOutputStream ostr = new ObjectOutputStream(fstr);
            Integer siz = new Integer(files.size());
            
            ostr.writeObject(siz);
            for(int i=0;i<siz;i++)
            {
                FileInfo fi = new FileInfo(files.get(i));
                ostr.writeObject(fi);
            }
            
            ostr.close();
        }
        catch(IOException ioex)
        {
            opt.err("Save io exception: " + ioex);
        }
        catch(Exception ex)
        {
            opt.err("Save exception: " + ex);
        }
    }
    
    void         save()
    {
        save(saveFile);
    }
}
