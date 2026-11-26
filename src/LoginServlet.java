import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String expectedUsername = System.getenv("SERVLET_LOGIN_USERNAME");
        String expectedPassword = System.getenv("SERVLET_LOGIN_PASSWORD");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q2 — Servlet Login");
        if (expectedUsername == null || expectedUsername.isBlank()
                || expectedPassword == null || expectedPassword.isBlank()) {
            out.println("<p class='error'>Set SERVLET_LOGIN_USERNAME and SERVLET_LOGIN_PASSWORD in your local config.local.bat to enable the demo login.</p>");
        } else if (expectedUsername.equals(username) && expectedPassword.equals(password)) {
            out.println("<p class='result'>Hello</p>");
        } else {
            out.println("<p class='error'>login failed</p>");
        }
        out.println("<p><a class='button' href='q2-login.html'>Return to login</a></p>");
        Html.end(out);
    }
}
