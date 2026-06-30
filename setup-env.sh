#!/bin/bash
# ============================================
# HIS System Environment Setup Script
# ============================================

echo "========================================"
echo "  HIS System Environment Setup"
echo "========================================"
echo ""

# Check if running as admin (Windows)
echo "[1/3] Installing JDK 8..."
echo "----------------------------------------"

# Check if Java is available
if command -v java &> /dev/null; then
    echo "Java found, checking version..."
    java -version 2>&1
else
    echo "Java not found in PATH"
fi

echo ""
echo "Please install the following manually if not installed:"
echo ""
echo "1. JDK 8 or 11:"
echo "   Download: https://adoptium.net/temurin/releases/?version=8"
echo "   Or run: winget install EclipseAdoptium.Temurin.8.JDK"
echo ""
echo "2. Apache Maven:"
echo "   Download: https://maven.apache.org/download.cgi"
echo "   Or run: winget install Apache.Maven"
echo ""
echo "3. MySQL 8:"
echo "   Download: https://dev.mysql.com/downloads/installer/"
echo "   Or run: winget install Oracle.MySQL"
echo ""
echo "After installation, please:"
echo "1. Close and reopen your terminal"
echo "2. Verify: java -version, mvn -version, mysql --version"
echo "3. Run: ./init-db.sh"
echo ""
