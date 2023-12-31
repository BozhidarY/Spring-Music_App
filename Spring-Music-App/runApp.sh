#!/bin/bash

# Read the app.run.mode property from application.properties
while IFS='=' read -r key value; do
    if [ "$key" == "app.run.mode" ]; then
        app_run_mode="$value"
    fi
done < "C:\Users\b1j2d\Desktop\Spring-Music_App\Spring-Music-App\src\main\resources\application.properties"

echo "$app_run_mode"

if [ "${app_run_mode,,}" == "console" ]; then
    echo "Running Console Application..."
    mvn exec:java
elif [ "${app_run_mode,,}" == "spring" ]; then
    echo "Running Spring Boot Application..."
    mvn spring-boot:run
else
    echo "Invalid or unset app.run.mode. Please set it to 'console' or 'spring' in application.properties"
fi
