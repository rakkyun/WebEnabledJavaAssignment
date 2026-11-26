@echo off
setlocal EnableExtensions EnableDelayedExpansion
set "PROJECT_DIR=%~dp0"
if "%PROJECT_DIR:~-1%"=="\" set "PROJECT_DIR=%PROJECT_DIR:~0,-1%"
if exist "%PROJECT_DIR%\config.local.bat" call "%PROJECT_DIR%\config.local.bat"
if not defined JAVA_HOME (
  echo ERROR: Set JAVA_HOME in the environment or in config.local.bat.
  exit /b 1
)
if not defined CATALINA_HOME (
  echo ERROR: Set CATALINA_HOME in the environment or in config.local.bat.
  exit /b 1
)
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "SERVLET_API=%CATALINA_HOME%\lib\servlet-api.jar"
set "WEBAPP=%PROJECT_DIR%\webapp"
set "DEST=%CATALINA_HOME%\webapps\WebEnabledJavaAssignment"

if not exist "%PROJECT_DIR%\src" (
  echo ERROR: Source folder not found: "%PROJECT_DIR%\src"
  exit /b 1
)
if not exist "%SERVLET_API%" (
  echo ERROR: Tomcat Servlet API not found: "%SERVLET_API%"
  exit /b 1
)
if not exist "%JAVA_HOME%\bin\javac.exe" (
  echo ERROR: JDK compiler not found under JAVA_HOME: "%JAVA_HOME%\bin\javac.exe"
  exit /b 1
)
if not exist "%WEBAPP%\WEB-INF\classes" mkdir "%WEBAPP%\WEB-INF\classes"

set "SOURCES="
for /r "%PROJECT_DIR%\src" %%F in (*.java) do set "SOURCES=!SOURCES! "%%~fF""
if not defined SOURCES (
  echo ERROR: No Java source files found.
  exit /b 1
)

echo Compiling Servlet sources with the installed JDK...
"%JAVA_HOME%\bin\javac.exe" -encoding UTF-8 -cp "%SERVLET_API%" -d "%WEBAPP%\WEB-INF\classes" !SOURCES!
if errorlevel 1 (
  echo ERROR: Compilation failed. The application was not deployed.
  exit /b 1
)

if not exist "%DEST%" mkdir "%DEST%"
echo Copying the web application into "%DEST%"...
robocopy "%WEBAPP%" "%DEST%" /E /COPY:DAT /DCOPY:DAT /R:1 /W:1
set "COPY_RESULT=!ERRORLEVEL!"
if !COPY_RESULT! GEQ 8 (
  echo ERROR: Deployment copy failed with robocopy code !COPY_RESULT!.
  exit /b 1
)
echo Build and deployment completed successfully.
echo Start Tomcat with start-tomcat.bat, then open http://localhost:8080/WebEnabledJavaAssignment/
exit /b 0
