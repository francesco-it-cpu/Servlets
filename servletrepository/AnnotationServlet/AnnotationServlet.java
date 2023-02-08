import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
   String name();
   String value();
}

@MyAnnotation (name = "URLServletName", value = "myservlet2")
public class AnnotationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");  
		LocalDateTime now = LocalDateTime.now();
		
		String messageBody = "<h1>This class was loaded with annotation! Served at: " + dtf.format(now) +"</h1>";
		int bodyLength = messageBody.length();	   
		
		String messageHeader = 	"HTTP/1.1 200 OK\r\n"+
				"Content-Type: text/html\r\n"+
				"Content-Length: " + bodyLength + "\r\n";
		
		PrintWriter out = response.getWriter();
		out.println(messageHeader);
		out.flush();
		out.println(messageBody);
		out.flush();
		
	}
	
}
