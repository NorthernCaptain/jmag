/*
 * JMSwingProgressMeter.java
 *
 * Created on 28 Èþëü 2006 ã., 15:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

import javax.swing.ProgressMonitor;

/**
 *
 * @author Leo
 */
public class JMSwingProgressMeter implements JIProgressMeter {
    
    long   totalValue;
    long   currentValue;
    String note = "";
    ProgressMonitor  monitor;
    boolean cancelled = false;
    
    /** Creates a new instance of JMSwingProgressMeter */
    public JMSwingProgressMeter(ProgressMonitor mon, long total) {
        totalValue = total;
        currentValue = 0;
        monitor = mon;
    }

    public synchronized void updateValue(long valueToAdd) {
        currentValue += valueToAdd;
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                monitor.setProgress(getCurrentPercent());
                cancelled = monitor.isCanceled();
            }
        });
    }

    public synchronized int getCurrentPercent() {
        return (int)(currentValue*100l/totalValue);
    }

    public synchronized void setNote(String inote) {
        this.note = inote;
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                monitor.setNote(note);
            }
        });
    }

    public synchronized void stop() {
        monitor.close();
    }

    public synchronized  boolean isCancelled() {
        return cancelled;
    }

    public synchronized void setMaximum(long max) {
        totalValue = max;
    }

}
