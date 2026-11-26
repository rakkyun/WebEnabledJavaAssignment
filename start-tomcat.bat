@echo off
setlocal
set "PROJECT_DIR=%~dp0"
if exist "%PROJECT_DIR%config.local.bat" call "%PROJECT_DIR%config.local.bat"
if not defined JAVA_HOME (
  echo ERROR: Set JAVA_HOME in the environment or in config.local.bat.
  exit /b 1
)
if not defined CATALINA_HOME (
  echo ERROR: Set CATALINA_HOME in the environment or in config.local.bat.
  exit /b 1
)
set "PATH=%JAVA_HOME%\bin;%PATH%"
if not exist "%CATALINA_HOME%\bin\startup.bat" (
  echo ERROR: Tomcat startup.bat was not found.
  exit /b 1
)
call "%CATALINA_HOME%\bin\startup.bat"
