import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q1 — Simple Calculator");
        try {
            double first = Double.parseDouble(request.getParameter("first"));
            double second = Double.parseDouble(request.getParameter("second"));
            String operation = request.getParameter("operation");
            double result;
            String symbol;
            switch (operation == null ? "" : operation) {
                case "add": result = first + second; symbol = "+"; break;
                case "subtract": result = first - second; symbol = "−"; break;
                case "multiply": result = first * second; symbol = "×"; break;
                case "divide":
                    if (second == 0) throw new IllegalArgumentException("Cannot divide by zero.");
                    result = first / second; symbol = "÷"; break;
                default: throw new IllegalArgumentException("Choose a valid operation.");
            }
            out.println("<p class='result'>" + first + " " + symbol + " " + second + " = " + result + "</p>");
        } catch (NumberFormatException | NullPointerException ex) {
            out.println("<p class='error'>Enter two valid numbers.</p>");
        } catch (IllegalArgumentException ex) {
            out.println("<p class='error'>" + Html.escape(ex.getMessage()) + "</p>");
        }
        out.println("<p><a class='button' href='q1-calculator.html'>Try another calculation</a></p>");
        Html.end(out);
    }
}
