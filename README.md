# 医院HIS系统

基于 Spring Boot 2.7 + Vue 3 + MySQL 8 的医院信息管理系统。

## 功能模块

### 1. 门诊挂号
- 患者建档/查询
- 门诊挂号（普通号/专家号/急诊号）
- 医生排班管理
- 挂号记录查询/退号

### 2. 医生工作站
- 今日待诊患者列表
- 门诊病历书写
- 诊断录入
- 处方开立

### 3. 门诊收费
- 待收费处方列表
- 收费结算（现金/微信/支付宝/医保）
- 收费记录查询
- 退费处理

### 4. 药房管理
- 药房发药
- 药品管理
- 库存管理
- 库存预警

### 5. 系统管理
- 用户管理
- 角色管理
- 科室管理
- 字典管理

## 技术栈

### 后端
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.3
- Spring Security + JWT
- MySQL 8
- Knife4j (Swagger)

### 前端
- Vue 3
- Vite 5
- Element Plus
- Pinia
- Vue Router

## 快速开始

### 环境要求
- JDK 8+ (推荐 JDK 8 或 11)
- Maven 3.6+
- Node.js 18+
- MySQL 8.x

### 方式一: 使用自动安装脚本 (推荐)

```powershell
# 1. 以管理员身份运行 PowerShell，执行环境安装
.\setup-env.ps1

# 2. 安装完成后，重新打开终端，执行数据库初始化
.\init-db.ps1

# 3. 启动后端 (新终端)
.\start-backend.ps1

# 4. 启动前端 (另一个终端)
.\start-frontend.ps1
```

### 方式二: 手动安装

#### 1. 初始化数据库

```bash
# 登录 MySQL
mysql -u root -p

# 执行建表脚本
source sql/init-schema.sql

# 导入初始数据
source sql/init-data.sql
```

#### 2. 启动后端

```bash
cd his-server
mvn spring-boot:run
```

后端启动后访问: http://localhost:8080/doc.html (API文档)

#### 3. 启动前端

```bash
cd his-web
npm install
npm run dev
```

前端启动后访问: http://localhost:5173

### 登录系统

- 管理员账号: admin / 123456
- 医生账号: doctor01 / 123456
- 收费员账号: cashier01 / 123456
- 药剂师账号: pharmacist01 / 123456

## 业务流程

```
挂号 → 就诊 → 收费 → 发药
```

1. **挂号**: 挂号员为患者选择科室、医生、挂号类型，生成挂号单
2. **就诊**: 医生查看待诊患者，书写病历、录入诊断、开立处方
3. **收费**: 收费员查看待收费处方，进行费用结算
4. **发药**: 药剂师查看待发药处方，确认发药，自动扣减库存

## 项目结构

```
hospital-his/
├── his-server/          # 后端 Spring Boot 项目
│   ├── pom.xml
│   └── src/
├── his-web/             # 前端 Vue 项目
│   ├── package.json
│   └── src/
├── sql/                 # 数据库脚本
│   ├── init-schema.sql
│   └── init-data.sql
├── setup-env.ps1        # 环境安装脚本
├── init-db.ps1          # 数据库初始化脚本
├── start-backend.ps1    # 后端启动脚本
├── start-frontend.ps1   # 前端启动脚本
└── README.md
```
