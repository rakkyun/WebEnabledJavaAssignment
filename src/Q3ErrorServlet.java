import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Included by PasswordValidationServlet when the password is incorrect. */
@WebServlet("/q3-error")
public class Q3ErrorServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q3 — RequestDispatcher Password Check");
        out.println("<p class='error'>Incorrect password. Please try again.</p>");
        out.println("<p>Enter the exact password <code>Servlet</code> to continue.</p>");
        out.println("<form action='validate-password' method='post'><p><label for='password'>Password</label>");
        out.println("<input id='password' name='password' type='password'></p><button type='submit'>Validate</button></form>");
        Html.end(out);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { doGet(request, response); }
}
