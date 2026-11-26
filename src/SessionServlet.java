import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("destroy".equals(action)) {
            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Html.start(out, "Q5 — Session Demonstration");
            out.println("<p class='result'>Session destroyed. Reload to create a new session.</p>");
            out.println("<p><a class='button' href='session'>Create a session</a></p>");
            Html.end(out);
            return;
        }
        HttpSession session = request.getSession(true);
        Integer visits = (Integer) session.getAttribute("visits");
        boolean firstVisit = visits == null;
        visits = firstVisit ? 1 : visits + 1;
        session.setAttribute("visits", visits);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q5 — Session Demonstration");
        out.println("<p class='result'>" + (firstVisit ? "This is your first visit in this session." : "You have visited before in this session.") + "</p>");
        out.println("<p>Visit count in this session: " + visits + "</p>");
        out.println("<p>Session ID: <code>" + Html.escape(session.getId()) + "</code></p>");
        out.println("<p><a class='button' href='session?action=destroy'>Destroy session</a></p>");
        Html.end(out);
    }
}
