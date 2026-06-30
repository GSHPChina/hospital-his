# ============================================
# HIS系统数据库初始化脚本
# ============================================

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  HIS系统数据库初始化" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查 MySQL 是否可用
$mysqlAvailable = $false
try {
    $mysqlVersion = & mysql --version 2>&1
    if ($mysqlVersion -match "mysql") {
        $mysqlAvailable = $true
    }
} catch {}

if (-not $mysqlAvailable) {
    Write-Host "[错误] mysql 命令不可用，请确保 MySQL 已安装并配置环境变量" -ForegroundColor Red
    Write-Host "可能需要重启终端或手动添加 MySQL 到 PATH" -ForegroundColor Yellow
    pause
    exit
}

# 获取 MySQL 密码
$password = Read-Host "请输入 MySQL root 密码 (无密码直接回车)"

# 构建命令参数
if ([string]::IsNullOrEmpty($password)) {
    $mysqlCmd = "mysql -u root"
} else {
    $mysqlCmd = "mysql -u root -p$password"
}

Write-Host ""
Write-Host "[1/2] 创建数据库和表..." -ForegroundColor Green

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$schemaFile = Join-Path $scriptDir "sql\init-schema.sql"
$dataFile = Join-Path $scriptDir "sql\init-data.sql"

# 执行建表脚本
Invoke-Expression "$mysqlCmd -e `"source $schemaFile`""
if ($LASTEXITCODE -eq 0) {
    Write-Host "数据库表创建成功！" -ForegroundColor Green
} else {
    Write-Host "数据库表创建失败，请检查错误信息" -ForegroundColor Red
    pause
    exit
}

Write-Host ""
Write-Host "[2/2] 导入初始数据..." -ForegroundColor Green

# 执行数据导入脚本
Invoke-Expression "$mysqlCmd -e `"source $dataFile`""
if ($LASTEXITCODE -eq 0) {
    Write-Host "初始数据导入成功！" -ForegroundColor Green
} else {
    Write-Host "初始数据导入失败，请检查错误信息" -ForegroundColor Red
    pause
    exit
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  数据库初始化完成！" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "数据库: his_db" -ForegroundColor White
Write-Host "表数量: 20张" -ForegroundColor White
Write-Host ""
pause
