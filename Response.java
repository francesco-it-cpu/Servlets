import java.util.Collection;
import javax.servlet.http.Cookie;
import javax.servlet.ServletOutputStream;
import java.util.Locale;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;



public class Response implements HttpServletResponse
{
    Request request;
    OutputStream output;
    PrintWriter writer;
    
    public Response(final OutputStream output) throws IOException {
        this.output = output;
        this.writer = this.getWriter();
    }
    
    public void setRequest(final Request request) {
        this.request = request;
    }
    
    public void sendStaticResource() throws IOException {
        if (this.request.getUri().equals(MyHttpServer.getShutdownCommand())) {
            final String successMessage = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 25\r\n\r\n<h1>Shutting Down...</h1>";
            this.writer.print(successMessage);
            this.writer.flush();
        }
        else {
            FileInputStream fis = null;
            final String filePath = "." + this.request.getUri();
            try {
                final File file = new File(filePath);
                fis = new FileInputStream(file);
                final Scanner in = new Scanner(file);
                final StringBuilder sBuilder = new StringBuilder(50);
                while (in.hasNextLine()) {
                    sBuilder.append(in.nextLine());
                    sBuilder.append("\n");
                }
                in.close();
                final String successMessage2 = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + sBuilder.length() + "\r\n" + "\r\n";
                this.writer.print(successMessage2);
                this.writer.flush();
                this.writer.print(sBuilder);
                this.writer.flush();
            }
            catch (FileNotFoundException e) {
                final int totalLength = filePath.length() + 30;
                final String errorMessage = "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: " + totalLength + "\r\n" + "\r\n" + "<h1>File Not Found</h1>" + "<p>" + filePath + "</p>";
                this.writer.print(errorMessage);
                this.writer.flush();
                return;
            }
            finally {
                if (fis != null) {
                    fis.close();
                }
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
    
    public void flushBuffer() throws IOException {
    }
    
    public int getBufferSize() {
        return 0;
    }
    
    public String getCharacterEncoding() {
        return null;
    }
    
    public Locale getLocale() {
        return null;
    }
    
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }
    
    public PrintWriter getWriter() throws IOException {
        return this.writer = new PrintWriter(this.output, true);
    }
    
    public boolean isCommitted() {
        return false;
    }
    
    public void reset() {
    }
    
    public void resetBuffer() {
    }
    
    public void setBufferSize(final int size) {
    }
    
    public void setContentLength(final int length) {
    }
    
    public void setContentType(final String type) {
    }
    
    public void setLocale(final Locale locale) {
    }
    
    public String getContentType() {
        return null;
    }
    
    public void setCharacterEncoding(final String arg0) {
    }
    
    public void setContentLengthLong(final long arg0) {
    }
    
    public void addCookie(final Cookie cookie) {
    }
    
    public boolean containsHeader(final String name) {
        return false;
    }
    
    public String encodeURL(final String url) {
        return null;
    }
    
    public String encodeRedirectURL(final String url) {
        return null;
    }
    
    public String encodeUrl(final String url) {
        return null;
    }
    
    public String encodeRedirectUrl(final String url) {
        return null;
    }
    
    public void sendError(final int sc, final String msg) throws IOException {
    }
    
    public void sendError(final int sc) throws IOException {
    }
    
    public void sendRedirect(final String location) throws IOException {
    }
    
    public void setDateHeader(final String name, final long date) {
    }
    
    public void addDateHeader(final String name, final long date) {
    }
    
    public void setHeader(final String name, final String value) {
    }
    
    public void addHeader(final String name, final String value) {
    }
    
    public void setIntHeader(final String name, final int value) {
    }
    
    public void addIntHeader(final String name, final int value) {
    }
    
    public void setStatus(final int sc) {
    }
    
    public void setStatus(final int sc, final String sm) {
    }
    
    public int getStatus() {
        return 0;
    }
    
    public String getHeader(final String name) {
        return null;
    }
    
    public Collection<String> getHeaders(final String name) {
        return null;
    }
    
    public Collection<String> getHeaderNames() {
        return null;
    }
}
