import java.util.Enumeration;
import javax.servlet.http.HttpServlet;
import java.util.Hashtable;



public class ServletHashTable
{
    static Hashtable<String, HttpServlet> ht;
    
    ServletHashTable() {
    }
    
    static void initTable() {
        ServletHashTable.ht = new Hashtable<String, HttpServlet>();
    }
    
    static void put(final String s, final HttpServlet h) {
        ServletHashTable.ht.put(s, h);
    }
    
    static boolean contains(final String s) {
        return ServletHashTable.ht.containsKey(s);
    }
    
    static HttpServlet get(final String s) {
        return ServletHashTable.ht.get(s);
    }
    
    static void remove(final String s) {
        ServletHashTable.ht.remove(s);
    }
    
    static Enumeration<String> enu() {
        return ServletHashTable.ht.keys();
    }
}
