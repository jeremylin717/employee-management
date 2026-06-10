@REM Maven Wrapper for Windows
@echo off
setlocal

if not defined JAVA_HOME (
    if exist "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot" (
        set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
    ) else if exist "F:\IntelliJ IDEA 2025.3.2\jbr" (
        set "JAVA_HOME=F:\IntelliJ IDEA 2025.3.2\jbr"
    )
)

set "MAVEN_PROJECTBASEDIR=%CD%"

if not defined MAVEN_OPTS (
    set "MAVEN_OPTS=-Xmx1024m"
)

"%JAVA_HOME%\bin\java.exe" %MAVEN_OPTS% ^
    -classpath "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" ^
    "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" ^
    org.apache.maven.wrapper.MavenWrapperMain %*
