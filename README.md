# Web Enabled Java Programming Lab — Assignment 1

Plain Java Servlets and HTML for all ten MCA Web Enabled Java Programming Lab questions. This project uses `javax.servlet.*` and Apache Tomcat 9. It does not require Maven, Gradle, JavaScript or a special IDE.

## Requirements and one-time setup

- JDK 21 (or another compatible JDK)
- Apache Tomcat 9
- Windows batch files and `robocopy` for the included build/deploy workflow

Set `JAVA_HOME` to the JDK folder and `CATALINA_HOME` to the Tomcat 9 folder. For a convenient local setup, copy `config.example.bat` to `config.local.bat`, then edit the two paths and choose local Q2 demo login values. `config.local.bat` is ignored by Git and must not be committed. The Servlet API is taken from `%CATALINA_HOME%\lib\servlet-api.jar`.

## Compile and deploy

Open this folder in VS Code or a terminal. Run `build-and-deploy.bat` to compile every Java source into `webapp\WEB-INF\classes` and copy the web application to Tomcat's `webapps\WebEnabledJavaAssignment` directory. The copy is additive; it does not delete existing files or uploads. No ROOT or built-in Tomcat application is overwritten.

Run `start-tomcat.bat` to start Tomcat and `stop-tomcat.bat` to stop it. After rebuilding while Tomcat is running, restart Tomcat so it loads the updated Servlet classes.

## Homepage and question URLs

Homepage: <http://localhost:8080/WebEnabledJavaAssignment/>

1. Q1 — Calculator: <http://localhost:8080/WebEnabledJavaAssignment/q1-calculator.html>
2. Q2 — Login: <http://localhost:8080/WebEnabledJavaAssignment/q2-login.html>
3. Q3 — RequestDispatcher: <http://localhost:8080/WebEnabledJavaAssignment/q3-password.html>
4. Q4 — Cookie visit counter: <http://localhost:8080/WebEnabledJavaAssignment/cookie-counter>
5. Q5 — Session creation and destruction: <http://localhost:8080/WebEnabledJavaAssignment/session>
6. Q6 — File upload/download: <http://localhost:8080/WebEnabledJavaAssignment/q6-upload.html>
7. Q7 — Current date and time: <http://localhost:8080/WebEnabledJavaAssignment/date-time>
8. Q8 — HTML hyperlink to Servlet: <http://localhost:8080/WebEnabledJavaAssignment/q8-hyperlink.html>
9. Q9 — Automatic page refresh: <http://localhost:8080/WebEnabledJavaAssignment/refresh>
10. Q10 — Registration validation: <http://localhost:8080/WebEnabledJavaAssignment/q10-registration.html>

## Test instructions

- Q1: calculate `18 / 3`; then try division by zero.
- Q2: set `SERVLET_LOGIN_USERNAME` and `SERVLET_LOGIN_PASSWORD` in `config.local.bat`; use those exact values for the success case, then change one field for `login failed`.
- Q3: submit the assignment's exact password `Servlet`; then try a different value and confirm the error and form are displayed.
- Q4: reload the Servlet and verify the cookie-backed count increases.
- Q5: reload to see a repeat visit, then select **Destroy session** and revisit.
- Q6: upload a small file, open **View uploaded files**, then click its name to download it. Maximum size is 5 MB. Files are stored under the deployed app's `uploads` folder; unsafe names and traversal paths are rejected.
- Q7: refresh and check the current server date/time.
- Q8: click the HTML hyperlink and confirm its message appears in the Servlet response.
- Q9: confirm the displayed server time changes every five seconds; it uses the HTTP `Refresh` header and no JavaScript.
- Q10 valid example: First `Asha`, Last `Rao`, Login `asha`, Password and confirmation `Abc123!`. For invalid cases, enter a name with a digit, use a password missing a number or special character, leave a compulsory field empty, or mismatch the confirmation. Validation is server-side; the form has no `required`, `pattern` or JavaScript validation.

## Programs

Q1 arithmetic; Q2 demo login; Q3 RequestDispatcher forwarding and inclusion; Q4 Cookies; Q5 HttpSession visit detection and invalidation; Q6 multipart upload and path-checked download; Q7 Java date/time; Q8 hyperlink request data; Q9 automatic refresh; Q10 server-side registration validation.

The Q2 demo login values are supplied locally through environment variables and are not stored in the committed source. This classroom login is not production authentication. Q3's password literal is part of the assignment requirement.
