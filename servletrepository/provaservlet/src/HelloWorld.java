import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;


public class HelloWorld extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	private String message;
   
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  
	   LocalDateTime now = LocalDateTime.now();
	   
	   String messageBody = "<h1>Here I am! Served at: " + dtf.format(now) +"</h1>";
	   int bodyLength = messageBody.length();	   
	   
	   String messageHeader = 	"HTTP/1.1 200 OK\r\n"+
								"Content-Type: text/html\r\n"+
								"Content-Length: " + bodyLength + "\r\n";
	   
//	   message = messageHeader + messageBody;
	   PrintWriter out = response.getWriter();
	   out.println(messageHeader);
	   out.flush();
	   out.println(messageBody);
	   out.flush();
	   
   }
}