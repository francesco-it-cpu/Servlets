import java.io.IOException;
import java.net.Socket;



public class MyThread extends Thread
{
    private Socket localThreadSocket;
    private MyHttpServer serv;//di tipo MyHttpServer
    
    MyThread(final MyHttpServer serv, final Socket sock) {
        this.localThreadSocket = sock;
        this.serv = serv;
    }
    
    @Override
    public void run() {
        try {
            this.serv.processRequest(this.localThreadSocket);//chiama il metodo processRequest di MyHttpServer per processare la richesta
        }
        catch (IOException e) {
            Logger.logToFile("Process Request:   " + e.toString());
        }
    }
}
