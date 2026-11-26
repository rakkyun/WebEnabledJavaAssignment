import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie-counter")
public class CookieCounterServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int count = 0;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) for (Cookie cookie : cookies) {
            if ("visitCount".equals(cookie.getName())) {
                try { count = Integer.parseInt(cookie.getValue()); } catch (NumberFormatException ignored) { count = 0; }
            }
        }
        count++;
        Cookie counter = new Cookie("visitCount", Integer.toString(count));
        counter.setMaxAge(60 * 60 * 24 * 30);
        counter.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
        response.addCookie(counter);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q4 — Cookie Visit Counter");
        out.println("<p class='result'>You have visited this servlet " + count + " time(s).</p>");
        out.println("<p>Refresh this page to see the cookie count increase.</p>");
        Html.end(out);
    }
}
