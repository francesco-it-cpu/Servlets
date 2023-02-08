import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import javax.servlet.http.Cookie;
import javax.servlet.DispatcherType;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.util.Map;
import java.util.Locale;
import javax.servlet.ServletInputStream;
import javax.servlet.RequestDispatcher;
import java.util.Enumeration;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;


public class Request implements HttpServletRequest
{
    private InputStream input;
    private String uri;
    
    public Request(final InputStream input) {
        this.input = input;
    }
    
    public String getUri() {
        return this.uri;
    }
    
    private String parseUri(final String requestString) {
        final int index1 = requestString.indexOf(32);
        if (index1 != -1) {
            final int index2 = requestString.indexOf(32, index1 + 1);
            if (index2 > index1) {
                return requestString.substring(index1 + 1, index2);
            }
        }
        return null;
    }
    
    public void parse() {
        final StringBuffer request = new StringBuffer(2048);
        final byte[] buffer = new byte[2048];
        int i;
        try {
            i = this.input.read(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
            i = -1;
            System.exit(1);
        }
        for (int j = 0; j < i; ++j) {
            request.append((char)buffer[j]);
        }
        this.uri = this.parseUri(request.toString());
    }
    
    public Object getAttribute(final String attribute) {
        return null;
    }
    
    public Enumeration<String> getAttributeNames() {
        return null;
    }
    
    public String getRealPath(final String path) {
        return null;
    }
    
    public RequestDispatcher getRequestDispatcher(final String path) {
        return null;
    }
    
    public boolean isSecure() {
        return false;
    }
    
    public String getCharacterEncoding() {
        return null;
    }
    
    public int getContentLength() {
        return 0;
    }
    
    public String getContentType() {
        return null;
    }
    
    public ServletInputStream getInputStream() throws IOException {
        return null;
    }
    
    public Locale getLocale() {
        return null;
    }
    
    public Enumeration<Locale> getLocales() {
        return null;
    }
    
    public String getParameter(final String name) {
        return null;
    }
    
    public Map<String, String[]> getParameterMap() {
        return null;
    }
    
    public Enumeration<String> getParameterNames() {
        return null;
    }
    
    public String[] getParameterValues(final String parameter) {
        return null;
    }
    
    public String getProtocol() {
        return null;
    }
    
    public BufferedReader getReader() throws IOException {
        return null;
    }
    
    public String getRemoteAddr() {
        return null;
    }
    
    public String getRemoteHost() {
        return null;
    }
    
    public String getScheme() {
        return null;
    }
    
    public String getServerName() {
        return null;
    }
    
    public int getServerPort() {
        return 0;
    }
    
    public void removeAttribute(final String attribute) {
    }
    
    public void setAttribute(final String key, final Object value) {
    }
    
    public void setCharacterEncoding(final String encoding) throws UnsupportedEncodingException {
    }
    
    public long getContentLengthLong() {
        return 0L;
    }
    
    public int getRemotePort() {
        return 0;
    }
    
    public String getLocalName() {
        return null;
    }
    
    public String getLocalAddr() {
        return null;
    }
    
    public int getLocalPort() {
        return 0;
    }
    
    public ServletContext getServletContext() {
        return null;
    }
    
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }
    
    public AsyncContext startAsync(final ServletRequest servletRequest, final ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }
    
    public boolean isAsyncStarted() {
        return false;
    }
    
    public boolean isAsyncSupported() {
        return false;
    }
    
    public AsyncContext getAsyncContext() {
        return null;
    }
    
    public DispatcherType getDispatcherType() {
        return null;
    }
    
    public String getAuthType() {
        return null;
    }
    
    public Cookie[] getCookies() {
        return null;
    }
    
    public long getDateHeader(final String name) {
        return 0L;
    }
    
    public String getHeader(final String name) {
        return null;
    }
    
    public Enumeration<String> getHeaders(final String name) {
        return null;
    }
    
    public Enumeration<String> getHeaderNames() {
        return null;
    }
    
    public int getIntHeader(final String name) {
        return 0;
    }
    
    public String getMethod() {
        final String s = "GET";
        return s;
    }
    
    public String getPathInfo() {
        return null;
    }
    
    public String getPathTranslated() {
        return null;
    }
    
    public String getContextPath() {
        return null;
    }
    
    public String getQueryString() {
        return null;
    }
    
    public String getRemoteUser() {
        return null;
    }
    
    public boolean isUserInRole(final String role) {
        return false;
    }
    
    public Principal getUserPrincipal() {
        return null;
    }
    
    public String getRequestedSessionId() {
        return null;
    }
    
    public String getRequestURI() {
        return null;
    }
    
    public StringBuffer getRequestURL() {
        return null;
    }
    
    public String getServletPath() {
        return null;
    }
    
    public HttpSession getSession(final boolean create) {
        return null;
    }
    
    public HttpSession getSession() {
        return null;
    }
    
    public String changeSessionId() {
        return null;
    }
    
    public boolean isRequestedSessionIdValid() {
        return false;
    }
    
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }
    
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }
    
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }
    
    public boolean authenticate(final HttpServletResponse response) throws IOException, ServletException {
        return false;
    }
    
    public void login(final String username, final String password) throws ServletException {
    }
    
    public void logout() throws ServletException {
    }
    
    public Collection<Part> getParts() throws IOException, ServletException {
        return null;
    }
    
    public Part getPart(final String name) throws IOException, ServletException {
        return null;
    }
    
    public <T extends HttpUpgradeHandler> T upgrade(final Class<T> httpUpgradeHandlerClass) throws IOException, ServletException {
        return null;
    }
}
