import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hyperlink")
public class HyperlinkServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String message = request.getParameter("message");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q8 — Hyperlink Communication");
        out.println("<p class='result'>Message received from the HTML hyperlink: <strong>"
                + Html.escape(message == null ? "(no message)" : message) + "</strong></p>");
        Html.end(out);
    }
}
