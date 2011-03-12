/*
 * JMProcessor.java
 *
 * Created on 28 Èþëü 2006 ã., 9:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import java.awt.Toolkit;
import java.io.File;
import java.util.Vector;

/**
 *
 * @author Leo
 */
public class JMProcessor implements JIAlbumProcessor
{
    
    protected JMOptions   opt;
    
    /** Creates a new instance of JMProcessor */
    public JMProcessor(JMOptions options)
    {
        opt = options;
    }
    
    
    public JMAlbum generateAlbum(JMStore store)
    {
        Vector< Vector< JMFile > > vvec;
        Vector<JMFile> resultVec;
        
        int i;
        
        JIVecShuffler  shuffler = opt.getShuffler();
        
        //divide our source vector to vecotor of vectors by category
        vvec = opt.getCategoryDivider().divide(store.getFiles());
        
        //now we go though our vectors and shuffle each of them
        if(opt.isSubListShuffle())
        {
            for(i=0;i<vvec.size();i++)
                vvec.set(i, shuffler.shuffle(vvec.get(i)));
        }
        
        //now we flatten our vectors into one and limit it by size
        resultVec = opt.getPacker().flatten(vvec);
        
        if(opt.isResultShuffle())
            resultVec = shuffler.shuffle(resultVec);
        
        return new JMAlbum(resultVec,opt);
    }
    
    public void shuffleAlbum(JMStore store)
    {
        store.setFiles(opt.getShuffler().shuffle(store.getFiles()));
    }
    
    class CopyTask
    {
        JMStore album;
        File    destFolder;
        JIProgressMeter meter;
        CopyTask(JMStore album, File destFolder, JIProgressMeter meter)
        {
            this.album = album;
            this.destFolder = destFolder;
            this.meter = meter;
        }
        
        void run()
        {
            Vector<JMFile>  files = album.getFiles();
            JCopyFile       copy=new JCopyFile();
            String          dest=opt.getDestFolder().getAbsolutePath()
            + File.separator
                    + opt.getAlbumName();
            
            {
                File dir = new File(dest);
                dir.mkdir();
                dest += File.separator;
            }
                    
                    JIFileNumerator numerator = opt.getFileNumerator();
                    JICharConverter converter = opt.getCharConverter();
                    
                    for(int i=0; i<files.size();i++)
                    {
                        JMFile file = files.get(i);
                        String fname = null;
                        if(opt.isConvertNames())
                            fname = converter.convert(file.getFile().getName());
                        else
                            fname = file.getFile().getName();
                        fname = numerator.enumerate(fname);
                        String path = dest + fname;
                        if(meter != null)
                            meter.setNote(fname);
                        opt.dbg(file.getFile().getAbsolutePath() + " -> " + path);
                        copy.setFiles(file.getFile(), path);
                        if(!copy.copy(meter))
                            return;
                        
                        try
                        {
                            file.updateStat(opt.isSaveToID3());
                            
                            if(opt.isConvertID3())
                            {
                                JMFile tofl = new JMFile(new File(path));
                                tofl.initTag();
                                tofl.updateTag(converter, file.getFilledID3());
                            }
                        }
                        catch(Exception ex)
                        {
                            opt.err("Copy exception: " + ex);
                        }
                        if(meter!=null && meter.isCancelled())
                            return;
                    }
        }
    }
    
    private CopyTask copyTask = null;
    
    public void copyAlbumFiles(final JMagUI ui, final JMStore album, final JMStore source,
            final File destFolder, final JIProgressMeter meter)
    {
        
        final SwingWorker worker = new SwingWorker()
        {
            public Object construct()
            {
                copyTask = new CopyTask(album, destFolder, meter);
                copyTask.run();
                source.calculateParams();
                album.calculateParams();
                return copyTask;
            }
            public void finished()
            {
                Toolkit.getDefaultToolkit().beep();
                meter.stop();
                opt.nextAlbumCount();
                source.save();
                ui.updateSrcInfo();
                ui.updateAlbInfo();
            }
        };
        worker.start();
    }
    
}
