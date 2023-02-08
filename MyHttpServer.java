import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.net.ServerSocket;


public class MyHttpServer
{
    public static final String WEB_ROOT = "/Users/francescociarlo/Desktop/TUTTO/PrimoCompitino/SP/staticcontentrepository";
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown;
    private ServerSocket serverSocket;
    
    MyHttpServer() {
        this.shutdown = false;
    }
    
    public void await() {
        final ExecutorService pool = Executors.newFixedThreadPool(2);//threadPool
        this.serverSocket = null;
        final int port = 7654;
        try {
            this.serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        }
        catch (IOException e) {
            Logger.logToFile("MyHttpServer:\t" + e.toString());
            System.exit(1);
        }
        while (!this.shutdown) {
            Socket socket = null;
            try {
                socket = this.serverSocket.accept();
                if (Shutdown.flag) {
                    pool.shutdownNow();
                    return;
                }
                final MyThread T = new MyThread(this, socket);
                pool.execute((Runnable)T);//chiama metodo run() della classe MyThread()
            }
            catch (Exception ex) {}
        }
        pool.shutdownNow();
    }
    
    public void processRequest(final Socket socket) throws IOException {//chiamato dalla classe Mythread
        final InputStream input = socket.getInputStream();
        final OutputStream output = socket.getOutputStream();
        final Request request = new Request(input);
        request.parse();
        final Response response = new Response(output);
        response.setRequest(request);
        if (request.getUri() != null) {
            
                if (request.getUri().equals("/SHUTDOWN")) {
                    final MyStaticResourceProcessor processor = new MyStaticResourceProcessor();
                    processor.process(request, response);
                    this.shutdown = true;
                    try {
                        this.serverSocket.close();
                        System.exit(1);
                    }
                    catch (Exception e) {
                        return;
                    }
                }
                if (request.getUri().startsWith("/servlet")) {//request dinamic
                    final MyServletProcessor processor2 = new MyServletProcessor();
                    processor2.process(request, response);
                }
                else {
                    final MyStaticResourceProcessor processor = new MyStaticResourceProcessor();//richiesta statica
                    processor.process(request, response);
                }
            
            socket.close();
        }
    }//fine class ProcessRequest
    
    public static String getWebRoot() {
        return ".";
    }
    
    public static String getShutdownCommand() {
        return "/SHUTDOWN";
    }
    
    public boolean isShutdown() {
        return this.shutdown;
    }
    
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }
    
    public void setShutdown(final boolean shutdown) {
        this.shutdown = shutdown;
    }
    //main
    public static void main(final String[] args) {
        ServletHashTable.initTable();
        Logger.initLogger();
        try {
            final MyHttpServer myHttpServer = new MyHttpServer();
            final ManagementConsole managementConsole = new ManagementConsole(myHttpServer);
            managementConsole.start();//chiama il metodo run() di ManagmentConsole
            myHttpServer.await();
            System.out.println("Exiting...");
            managementConsole.stop();
            
        }
        catch (Exception e) {
            Logger.logToFile("Main:   " + e.toString());
        }
    }
}
