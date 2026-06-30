# ============================================
# HIS系统前端启动脚本
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  启动 HIS 前端服务" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$webDir = Join-Path $scriptDir "his-web"

# 检查 node_modules 是否存在
if (-not (Test-Path (Join-Path $webDir "node_modules"))) {
    Write-Host "首次启动，正在安装依赖..." -ForegroundColor Yellow
    Set-Location $webDir
    npm install
    Write-Host ""
}

Write-Host "正在启动 Vite 开发服务器..." -ForegroundColor Green
Write-Host ""
Write-Host "启动成功后访问: http://localhost:5173" -ForegroundColor Cyan
Write-Host "按 Ctrl+C 停止服务" -ForegroundColor Yellow
Write-Host ""

Set-Location $webDir
npm run dev
