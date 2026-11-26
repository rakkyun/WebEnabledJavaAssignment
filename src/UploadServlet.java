import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 6 * 1024 * 1024)
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private Path uploadFolder() throws IOException {
        String realPath = getServletContext().getRealPath("/uploads");
        if (realPath == null) throw new IOException("Tomcat must use an exploded web application.");
        Path folder = Paths.get(realPath).toAbsolutePath().normalize();
        Files.createDirectories(folder);
        return folder;
    }

    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q6 — Uploaded Files");
        Path folder = uploadFolder();
        out.println("<p>Files saved on this Tomcat installation:</p><ul>");
        try (java.nio.file.DirectoryStream<Path> files = Files.newDirectoryStream(folder)) {
            boolean any = false;
            for (Path file : files) {
                String name = file.getFileName().toString();
                if (Files.isRegularFile(file) && !name.startsWith(".")) {
                    any = true;
                    out.println("<li><a href='download?file=" + java.net.URLEncoder.encode(name, "UTF-8")
                            + "'>" + Html.escape(name) + "</a></li>");
                }
            }
            if (!any) out.println("<li>No files uploaded yet.</li>");
        }
        out.println("</ul><p><a class='button' href='q6-upload.html'>Upload another file</a></p>");
        Html.end(out);
    }

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Html.start(out, "Q6 — File Upload");
        try {
            Part part = request.getPart("file");
            if (part == null || part.getSize() == 0) throw new IllegalArgumentException("Choose a file first.");
            String submitted = part.getSubmittedFileName();
            if (submitted == null) throw new IllegalArgumentException("The uploaded file has no name.");
            // Strip both slash styles, reject suspicious names, then generate a unique stored name.
            String original = submitted.replace('\\', '/');
            original = original.substring(original.lastIndexOf('/') + 1);
            if (original.isBlank() || original.equals(".") || original.equals("..")
                    || original.contains("..") || !original.matches("[A-Za-z0-9._-]+")) {
                throw new IllegalArgumentException("Use a file name containing only letters, numbers, dot, underscore or hyphen.");
            }
            String storedName = UUID.randomUUID().toString() + "_" + original;
            Path folder = uploadFolder();
            Path destination = folder.resolve(storedName).normalize();
            if (!destination.getParent().equals(folder)) throw new IllegalArgumentException("Invalid file name.");
            part.write(destination.toString());
            out.println("<p class='result'>Upload successful: " + Html.escape(original) + "</p>");
            out.println("<p><a class='button' href='upload'>View files and download</a></p>");
        } catch (IllegalArgumentException ex) {
            out.println("<p class='error'>" + Html.escape(ex.getMessage()) + "</p>");
        } catch (IllegalStateException ex) {
            out.println("<p class='error'>Upload is too large. The maximum file size is 5 MB.</p>");
        }
        Html.end(out);
    }
}
