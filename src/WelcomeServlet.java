import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q3 — Welcome Servlet");
        out.println("<p class='result'>Welcome! The password was correct.</p>");
        Html.end(out);
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { doGet(request, response); }
}
