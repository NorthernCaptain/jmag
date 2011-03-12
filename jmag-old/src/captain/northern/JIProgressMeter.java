/*
 * JIProgressMeter.java
 *
 * Created on 28 Èþëü 2006 ã., 15:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

/**
 *
 * @author Leo
 */
public interface JIProgressMeter {
    
    void setMaximum(long max);
    
    //adds value to current progress
    void updateValue(long valueToAdd);
    
    //sets a note to the progress
    void setNote(String note);
    
    //return current progress in percents
    int getCurrentPercent();
    
    //stops progress and cleans up
    void stop();
    
    //return true if action was cancelled by our user
    boolean isCancelled();
}
