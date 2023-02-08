import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 


public class EnglishAlphabet extends HttpServlet {

   private static final long serialVersionUID = 1L;
   private String responseMessage;
   
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
	   String englishAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	   PrintWriter out = response.getWriter();
	   String responseHeader =	"HTTP/1.1 200 OK\r\n"+
								"Content-Type: text/html\r\n"+
								"Content-Length: 26\r\n";
	   out.println(responseHeader);  
	   
	   for (int i = 0; i < englishAlphabet.length(); i++) {
		   out.print(englishAlphabet.charAt(i));
		   out.flush();
		   try {
			   Thread.sleep(400);
		   } catch (InterruptedException e) {
			   e.printStackTrace();
		   }
	   }
	   
   }
}
