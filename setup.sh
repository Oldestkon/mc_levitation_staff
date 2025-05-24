#!/bin/bash

echo "========================================"
echo "Levitation Staff Mod - Setup Script"
echo "========================================"

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo
echo "Checking Java installation..."
if command -v java &> /dev/null; then
    echo -e "${GREEN}Java found!${NC}"
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d '"' -f 2)
    echo "Java version: $JAVA_VERSION"
    
    # Check if it's Java 17
    if [[ $JAVA_VERSION == 17.* ]]; then
        echo -e "${GREEN}✓ Java 17 detected${NC}"
    else
        echo -e "${YELLOW}⚠ Warning: Java 17 is recommended, but found: $JAVA_VERSION${NC}"
    fi
else
    echo -e "${RED}ERROR: Java is not installed or not in PATH${NC}"
    echo "Please install OpenJDK 17:"
    echo "  macOS: brew install openjdk@17"
    echo "  Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    echo "  Or download from: https://adoptium.net/"
    exit 1
fi

echo
echo "Setting up Gradle wrapper permissions..."
if [ -f "gradlew" ]; then
    chmod +x gradlew
    echo -e "${GREEN}✓ Gradle wrapper found and permissions set${NC}"
else
    echo -e "${RED}ERROR: gradlew not found. Are you in the correct directory?${NC}"
    exit 1
fi

echo
echo "Generating IDE run configurations..."
echo "Running: ./gradlew genIntellijRuns"
./gradlew genIntellijRuns
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Failed to generate IntelliJ runs${NC}"
    exit 1
fi

echo "Running: ./gradlew genEclipseRuns"
./gradlew genEclipseRuns
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Failed to generate Eclipse runs${NC}"
    exit 1
fi

echo
echo "Building the mod for the first time..."
echo "Running: ./gradlew build"
./gradlew build
if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: Build failed${NC}"
    echo "Check the error messages above"
    exit 1
fi

echo
echo "========================================"
echo -e "${GREEN}Setup Complete!${NC}"
echo "========================================"
echo
echo "Next steps:"
echo "1. Import the project in IntelliJ IDEA"
echo "2. Open build.gradle as a project"
echo "3. Wait for dependencies to download"
echo "4. Run the 'runClient' configuration to test"
echo
echo "The mod JAR has been created in: build/libs/"
echo
echo -e "${GREEN}Happy Modding! 🚀${NC}" 