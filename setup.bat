@echo off
echo ========================================
echo Levitation Staff Mod - Setup Script
echo ========================================

echo.
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install OpenJDK 17 from https://adoptium.net/
    pause
    exit /b 1
)

echo Java found! Checking version...
for /f "tokens=3" %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%a
)
echo Java version: %JAVA_VERSION%

echo.
echo Setting up Gradle wrapper permissions...
if exist gradlew.bat (
    echo Gradle wrapper found.
) else (
    echo ERROR: gradlew.bat not found. Are you in the correct directory?
    pause
    exit /b 1
)

echo.
echo Generating IDE run configurations...
echo Running: gradlew genIntellijRuns
call gradlew.bat genIntellijRuns
if %errorlevel% neq 0 (
    echo ERROR: Failed to generate IntelliJ runs
    pause
    exit /b 1
)

echo Running: gradlew genEclipseRuns  
call gradlew.bat genEclipseRuns
if %errorlevel% neq 0 (
    echo ERROR: Failed to generate Eclipse runs
    pause
    exit /b 1
)

echo.
echo Building the mod for the first time...
echo Running: gradlew build
call gradlew.bat build
if %errorlevel% neq 0 (
    echo ERROR: Build failed
    echo Check the error messages above
    pause
    exit /b 1
)

echo.
echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo Next steps:
echo 1. Import the project in IntelliJ IDEA
echo 2. Open build.gradle as a project
echo 3. Wait for dependencies to download
echo 4. Run the 'runClient' configuration to test
echo.
echo The mod JAR has been created in: build\libs\
echo.
pause 