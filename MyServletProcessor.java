import javax.servlet.http.HttpServlet;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;


public class MyServletProcessor
{
    public void process(final Request request, final Response response) {
        final String uri = request.getUri();
        final String servletName = uri.substring(uri.lastIndexOf("/") + 1);//prende il nome del servlet dopo servlet/(nome_servlet)
        if (ServletHashTable.contains(servletName)) {//vede se hashtable contiene il servle. se si ritorna il nome
            final HttpServlet servlet = ServletHashTable.get(servletName);
            try {
                servlet.service((ServletRequest)request, (ServletResponse)response);//start del servlet
            }
            catch (Exception e) {
                Logger.logToFile(e.toString());
            }
            catch (Throwable e2) {
                Logger.logToFile(e2.toString());
            }
        }
    }
}
