# 企业员工管理系统

基于 **Spring Boot 3.4** + **MySQL** + **Thymeleaf** + **Spring Data JPA** 构建的企业级员工信息管理系统。

## 功能特性

- ✅ **员工管理** - 员工的增删改查、按部门筛选、姓名搜索、分页展示
- ✅ **部门管理** - 部门的增删改查、模糊搜索、关联员工展示
- ✅ **一对多关联** - 部门-员工外键关联，部门详情页展示下属员工
- ✅ **分页查询** - Spring Data JPA 分页 + Thymeleaf 分页导航
- ✅ **数据校验** - Bean Validation 前端+后端双重校验
- ✅ **Bootstrap 5 界面** - 响应式布局、现代化 UI

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.4.3 | 核心框架 |
| Spring Data JPA | - | 数据持久层 |
| Thymeleaf | - | 模板引擎 |
| MySQL | 8.0+ | 数据库 |
| Bootstrap | 5.3 | 前端UI |
| Lombok | - | 代码简化 |

## 项目结构

```
employee-management/
├── pom.xml
├── src/main/java/com/example/employeemanagement/
│   ├── EmployeeManagementApplication.java  # 启动类
│   ├── model/
│   │   ├── Department.java                 # 部门实体
│   │   └── Employee.java                   # 员工实体（多对一关联部门）
│   ├── repository/
│   │   ├── DepartmentRepository.java       # 部门数据访问层
│   │   └── EmployeeRepository.java         # 员工数据访问层（分页+搜索）
│   ├── service/
│   │   ├── DepartmentService.java          # 部门业务逻辑
│   │   └── EmployeeService.java            # 员工业务逻辑
│   └── controller/
│       ├── HomeController.java             # 首页控制器
│       ├── DepartmentController.java       # 部门控制器（CRUD）
│       └── EmployeeController.java         # 员工控制器（CRUD+分页）
└── src/main/resources/
    ├── application.properties              # 应用配置
    ├── sql/init.sql                        # 数据库初始化脚本（含示例数据）
    ├── templates/
    │   ├── index.html                      # 首页仪表盘
    │   ├── fragments/layout.html           # 公共布局模板
    │   ├── departments/
    │   │   ├── list.html                   # 部门列表
    │   │   ├── form.html                   # 部门表单
    │   │   └── detail.html                 # 部门详情
    │   └── employees/
    │       ├── list.html                   # 员工列表（分页）
    │       ├── form.html                   # 员工表单
    │       └── detail.html                 # 员工详情
    └── static/css/style.css                # 自定义样式
```

## 快速开始

### 1. 环境要求

- JDK 21+
- Maven 3.8+
- MySQL 8.0+

### 2. 初始化数据库

```bash
mysql -u root -p < src/main/resources/sql/init.sql
```

### 3. 修改数据库配置

编辑 `src/main/resources/application.properties`，修改数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db?...
spring.datasource.username=你的用户名
spring.datasource.password=你的密码
```

### 4. 启动应用

```bash
mvn spring-boot:run
```

### 5. 访问系统

打开浏览器访问: [http://localhost:8088](http://localhost:8088)

## 页面路由

| 路径 | 说明 |
|------|------|
| `GET /` | 首页仪表盘 |
| `GET /employees` | 员工列表（支持搜索和分页） |
| `GET /employees/new` | 新增员工 |
| `GET /employees/edit/{id}` | 编辑员工 |
| `GET /employees/detail/{id}` | 员工详情 |
| `POST /employees/save` | 保存员工 |
| `POST /employees/delete/{id}` | 删除员工 |
| `GET /departments` | 部门列表 |
| `GET /departments/new` | 新增部门 |
| `GET /departments/edit/{id}` | 编辑部门 |
| `GET /departments/detail/{id}` | 部门详情 |
| `POST /departments/save` | 保存部门 |
| `POST /departments/delete/{id}` | 删除部门 |

## 数据库表关系

```
departments (1) ──→ (N) employees
   ┌─ id (PK)              ┌─ id (PK)
   ├─ name                 ├─ first_name
   ├─ description          ├─ last_name
   └─ created_date         ├─ email
                           ├─ phone
                           ├─ position
                           ├─ salary
                           ├─ hire_date
                           ├─ department_id (FK → departments.id)
                           ├─ created_date
                           └─ updated_date
```
