import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class PostComplaint extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameterNames());
        response.sendRedirect("success.html");
    }
}