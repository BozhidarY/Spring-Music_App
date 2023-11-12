@echo off

FOR /F "tokens=1,2 delims==" %%G IN (C:\Users\b1j2d\Desktop\Spring-Music_App\Spring-Music-App\src\main\resources\application.properties) DO (set %%G=%%H)  
echo %app.run.mode%


if /i "%app.run.mode%"=="console" (
    echo Running Console Application...
    mvn exec:java
) else if /i "%app.run.mode%"=="spring" (
    echo Running Spring Boot Application...
    mvn spring-boot:run
) else (
    echo Invalid or unset app.run.mode. Please set it to 'console' or 'spring' in %PROPERTIES_FILE%
)
