import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private String value(HttpServletRequest request, String name) {
        String text = request.getParameter(name);
        return text == null ? "" : text.trim();
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String first = value(request, "firstName");
        String middle = value(request, "middleName");
        String last = value(request, "lastName");
        String login = value(request, "login");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        String location = value(request, "location");
        String education = value(request, "education");
        String phone = value(request, "phone");
        if (password == null) password = "";
        if (confirm == null) confirm = "";

        List<String> errors = new ArrayList<>();
        if (first.isEmpty()) errors.add("First name is required.");
        else if (!first.matches("\\p{L}+")) errors.add("First name must contain alphabets only.");
        if (middle.length() > 0 && !middle.matches("\\p{L}+")) errors.add("Middle name must contain alphabets only when provided.");
        if (last.isEmpty()) errors.add("Last name is required.");
        else if (!last.matches("\\p{L}+")) errors.add("Last name must contain alphabets only.");
        if (login.isEmpty()) errors.add("Desired login is required.");
        if (password.isEmpty()) errors.add("Password is required.");
        else {
            if (password.length() < 6) errors.add("Password must be at least 6 characters long.");
            if (!password.matches(".*\\p{L}.*")) errors.add("Password must contain at least one alphabet.");
            if (!password.matches(".*\\d.*")) errors.add("Password must contain at least one number.");
            if (!password.matches(".*[^\\p{L}\\d\\s].*")) errors.add("Password must contain at least one special character.");
        }
        if (confirm.isEmpty()) errors.add("Confirm password is required.");
        else if (!password.equals(confirm)) errors.add("Password and confirm password do not match.");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q10 — Email Service Registration");
        if (errors.isEmpty()) {
            out.println("<p class='result'>Registration details are valid. Welcome, " + Html.escape(first) + "!</p>");
            out.println("<p>Desired login: " + Html.escape(login) + "</p>");
            if (!location.isEmpty()) out.println("<p>Location: " + Html.escape(location) + "</p>");
            if (!education.isEmpty()) out.println("<p>Education: " + Html.escape(education) + "</p>");
            if (!phone.isEmpty()) out.println("<p>Phone: " + Html.escape(phone) + "</p>");
        } else {
            out.println("<p class='error'>Please correct these problems:</p><ul>");
            for (String error : errors) out.println("<li class='error'>" + Html.escape(error) + "</li>");
            out.println("</ul><p>No browser-side checks are used; validation is performed by this Servlet.</p>");
            out.println("<p><a class='button' href='q10-registration.html'>Return to registration form</a></p>");
        }
        Html.end(out);
    }
}
