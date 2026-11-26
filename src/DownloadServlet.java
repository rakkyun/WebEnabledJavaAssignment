import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("file");
        if (name == null || !name.matches("[A-Za-z0-9._-]+") || name.contains("..")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid file name.");
            return;
        }
        String realPath = getServletContext().getRealPath("/uploads");
        if (realPath == null) { response.sendError(500, "Upload folder is unavailable."); return; }
        Path folder = Paths.get(realPath).toAbsolutePath().normalize();
        Path file = folder.resolve(name).normalize();
        if (!folder.equals(file.getParent()) || !Files.isRegularFile(file)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        response.setContentLengthLong(Files.size(file));
        try (java.io.InputStream input = Files.newInputStream(file);
             java.io.OutputStream output = response.getOutputStream()) {
            input.transferTo(output);
        }
    }
}
