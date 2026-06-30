# 医院HIS系统 - 环境配置指南

## 当前环境状态

| 组件 | 状态 | 说明 |
|------|------|------|
| Java | ❌ JRE 8 only | 需要安装 JDK 8 |
| Maven | ✅ 已包含 | 项目自带 Maven Wrapper |
| MySQL | ❌ 未安装 | 需要安装 |
| Node.js | ✅ v24.16.0 | 已就绪 |
| npm | ✅ v11.13.0 | 已就绪 |

---

## 必须安装

### 1. JDK 8 (必须是 JDK，不是 JRE)

**为什么需要 JDK？**
- JRE 只能运行 Java 程序
- JDK 包含编译器 (javac)，Maven 需要用它来编译你的代码

**下载地址：**
- **推荐 - Eclipse Temurin (免费)**: https://adoptium.net/temurin/releases/?version=8
  - 选择 Windows x64 `.msi` 安装包
  - 安装时勾选 "Set JAVA_HOME variable"
  - 安装时勾引 "Add to PATH"

- **Oracle JDK 8**: https://www.oracle.com/java/technologies/downloads/#java8-windows
  - 需要 Oracle 账号登录

**验证安装：**
```bash
java -version    # 应显示 1.8.x
javac -version   # 应显示 1.8.x
```

### 2. MySQL 8.x

**下载地址：** https://dev.mysql.com/downloads/mysql/

**安装步骤：**
1. 下载 MySQL Installer
2. 选择 "Custom" 安装
3. 安装 MySQL Server 8.x
4. 设置 root 密码（记住这个密码！）
5. 完成安装

**初始化数据库：**
```bash
# 打开命令提示符或 PowerShell
cd C:\Users\GSHP\hospital-his
mysql -u root -p < sql\init-schema.sql
mysql -u root -p his_db < sql\init-data.sql
```

---

## 启动步骤

### 1. 启动后端

```bash
cd C:\Users\GSHP\hospital-his\his-server

# Windows 使用 mvnw.cmd
mvnw.cmd spring-boot:run

# 或者在 Git Bash 中
./mvnw spring-boot:run
```

首次运行会自动下载 Maven 和依赖，需要几分钟。

启动成功后：
- API 文档: http://localhost:8080/doc.html

### 2. 启动前端

```bash
cd C:\Users\GSHP\hospital-his\his-web
npm install
npm run dev
```

访问: http://localhost:5173

---

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 医生 | doctor01 | 123456 |
| 收费员 | cashier01 | 123456 |
| 药剂师 | pharmacist01 | 123456 |

---

## 业务流程测试

1. 用 **admin** 登录 → 管理科室、用户、字典
2. 用 **admin** 创建患者 → 门诊挂号
3. 用 **doctor01** 登录 → 医生工作站 → 写病历、开处方
4. 用 **cashier01** 登录 → 门诊收费 → 确认收费
5. 用 **pharmacist01** 登录 → 药房发药 → 确认发药

---

## 常见问题

### Q: `javac` 命令找不到
说明你只安装了 JRE，需要安装 JDK。下载 Temurin JDK 8 并安装。

### Q: Maven 下载慢
创建文件 `C:\Users\GSHP\.m2\settings.xml`：
```xml
<settings>
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <mirrorOf>central</mirrorOf>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
</settings>
```

### Q: MySQL 连接失败
1. 检查 MySQL 服务是否启动：`services.msc` → 找到 MySQL
2. 检查密码是否正确（默认配置是 `root` / `root`）
3. 检查数据库是否已初始化

### Q: 端口被占用
修改 `application.yml` 中的 `server.port`
