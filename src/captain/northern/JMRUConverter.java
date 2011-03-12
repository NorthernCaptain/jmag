/*
 * JMRUConverter.java
 *
 * Created on 28 Èşëü 2006 ã., 14:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package captain.northern;

/**
 *
 * @author Leo
 */
public class JMRUConverter implements JICharConverter{
    
    static String filter="àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúüûışÿÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÜÛİŞß";
    static String filter_dest[] =
    {
        "a",
        "b",
        "v",
        "g",
        "d",
        "e",
        "e",
        "zg",
        "z",
        "i",
        "y",
        "k",
        "l",
        "m",
        "n",
        "o",
        "p",
        "r",
        "s",
        "t",
        "u",
        "f",
        "h",
        "c",
        "ch",
        "sh",
        "sch",
        "'",
        "'",
        "i",
        "e",
        "yu",
        "ya",
        "A",
        "B",
        "V",
        "G",
        "D",
        "E",
        "E",
        "ZG",
        "Z",
        "I",
        "Y",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "R",
        "S",
        "T",
        "U",
        "F",
        "H",
        "C",
        "CH",
        "SH",
        "SCH",
        "'",
        "'",
        "I",
        "E",
        "YU",
        "YA",
    };
    
    /** Creates a new instance of JMRUConverter */
    public JMRUConverter() {
    }

    public String convert(String src) {
        String res = "";
        
        for(int i=0;i<src.length();i++)
        {
            int idx = filter.indexOf(src.codePointAt(i));
            if(idx == -1)
            {
                res += src.charAt(i);
            } else
                res += filter_dest[idx];            
        }
        return res;
    }

}
