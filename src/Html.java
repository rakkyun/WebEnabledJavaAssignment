import java.io.PrintWriter;

/** Small HTML helpers shared by the demonstration servlets. */
public final class Html {
    private Html() { }

    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public static void start(PrintWriter out, String title) {
        out.println("<!doctype html><html lang='en'><head><meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>" + escape(title) + "</title>");
        out.println("<link rel='stylesheet' href='style.css'></head><body><main class='card'>");
        out.println("<p><a href='index.html'>← Assignment home</a></p><h1>" + escape(title) + "</h1>");
    }

    public static void end(PrintWriter out) {
        out.println("</main></body></html>");
    }
}
