# ============================================
# HIS系统后端启动脚本
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  启动 HIS 后端服务" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查 Maven 是否可用
$mvnAvailable = $false
try {
    $mvnVersion = & mvn -version 2>&1
    if ($mvnVersion -match "Apache Maven") {
        $mvnAvailable = $true
    }
} catch {}

if (-not $mvnAvailable) {
    Write-Host "[错误] mvn 命令不可用，请确保 Maven 已安装并配置环境变量" -ForegroundColor Red
    Write-Host "可能需要重启终端或手动添加 Maven 到 PATH" -ForegroundColor Yellow
    pause
    exit
}

Write-Host "正在启动 Spring Boot 应用..." -ForegroundColor Green
Write-Host "首次启动需要下载依赖，请耐心等待..." -ForegroundColor Yellow
Write-Host ""
Write-Host "启动成功后访问: http://localhost:8080/doc.html" -ForegroundColor Cyan
Write-Host "按 Ctrl+C 停止服务" -ForegroundColor Yellow
Write-Host ""

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$serverDir = Join-Path $scriptDir "his-server"

Set-Location $serverDir
mvn spring-boot:run
