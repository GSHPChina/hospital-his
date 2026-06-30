# ============================================
# HIS系统环境自动安装脚本
# 以管理员身份运行 PowerShell 执行此脚本
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  HIS系统环境自动安装脚本" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查是否以管理员身份运行
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
if (-not $isAdmin) {
    Write-Host "[错误] 请以管理员身份运行此脚本！" -ForegroundColor Red
    Write-Host "右键点击 PowerShell -> 以管理员身份运行" -ForegroundColor Yellow
    pause
    exit
}

# ============================================
# 1. 安装 JDK 8 (Temurin)
# ============================================
Write-Host ""
Write-Host "[1/3] 安装 JDK 8..." -ForegroundColor Green
Write-Host "----------------------------------------"

$jdkInstalled = $false
# 检查是否已有 JDK
try {
    $javaHome = $env:JAVA_HOME
    if ($javaHome -and (Test-Path "$javaHome\bin\javac.exe")) {
        $jdkInstalled = $true
    }
} catch {}

if (-not $jdkInstalled) {
    try {
        $javacVersion = & javac -version 2>&1
        if ($javacVersion -match "javac") {
            $jdkInstalled = $true
        }
    } catch {}
}

if ($jdkInstalled) {
    Write-Host "JDK 已安装，跳过..." -ForegroundColor Yellow
} else {
    Write-Host "正在通过 winget 安装 Eclipse Temurin JDK 8..." -ForegroundColor Cyan
    winget install EclipseAdoptium.Temurin.8.JDK --accept-package-agreements --accept-source-agreements
    if ($LASTEXITCODE -eq 0) {
        Write-Host "JDK 8 安装成功！" -ForegroundColor Green
    } else {
        Write-Host "JDK 8 安装可能失败，请手动安装" -ForegroundColor Red
        Write-Host "下载地址: https://adoptium.net/temurin/releases/?version=8" -ForegroundColor Yellow
    }
}

# ============================================
# 2. 安装 Maven
# ============================================
Write-Host ""
Write-Host "[2/3] 安装 Apache Maven..." -ForegroundColor Green
Write-Host "----------------------------------------"

$mavenInstalled = $false
try {
    $mavenVersion = & mvn -version 2>&1
    if ($mavenVersion -match "Apache Maven") {
        $mavenInstalled = $true
    }
} catch {}

if ($mavenInstalled) {
    Write-Host "Maven 已安装，跳过..." -ForegroundColor Yellow
} else {
    Write-Host "正在通过 winget 安装 Apache Maven..." -ForegroundColor Cyan
    winget install Apache.Maven --accept-package-agreements --accept-source-agreements
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Maven 安装成功！" -ForegroundColor Green
    } else {
        Write-Host "Maven 安装可能失败，请手动安装" -ForegroundColor Red
        Write-Host "下载地址: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    }
}

# ============================================
# 3. 安装 MySQL
# ============================================
Write-Host ""
Write-Host "[3/3] 安装 MySQL 8..." -ForegroundColor Green
Write-Host "----------------------------------------"

$mysqlInstalled = $false
try {
    $mysqlVersion = & mysql --version 2>&1
    if ($mysqlVersion -match "mysql") {
        $mysqlInstalled = $true
    }
} catch {}

if ($mysqlInstalled) {
    Write-Host "MySQL 已安装，跳过..." -ForegroundColor Yellow
} else {
    Write-Host "正在通过 winget 安装 MySQL 8..." -ForegroundColor Cyan
    winget install Oracle.MySQL --accept-package-agreements --accept-source-agreements
    if ($LASTEXITCODE -eq 0) {
        Write-Host "MySQL 安装成功！" -ForegroundColor Green
        Write-Host "注意: MySQL 安装后可能需要重启终端才能使用 mysql 命令" -ForegroundColor Yellow
    } else {
        Write-Host "MySQL 安装可能失败，请手动安装" -ForegroundColor Red
        Write-Host "下载地址: https://dev.mysql.com/downloads/installer/" -ForegroundColor Yellow
    }
}

# ============================================
# 完成
# ============================================
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  安装完成！" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "请执行以下操作：" -ForegroundColor Yellow
Write-Host "1. 关闭当前终端，重新打开一个新的终端" -ForegroundColor White
Write-Host "2. 验证安装:" -ForegroundColor White
Write-Host "   java -version" -ForegroundColor Gray
Write-Host "   mvn -version" -ForegroundColor Gray
Write-Host "   mysql --version" -ForegroundColor Gray
Write-Host "3. 然后运行数据库初始化和启动脚本" -ForegroundColor White
Write-Host ""
pause
