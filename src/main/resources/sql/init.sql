-- =============================================
-- 企业员工管理系统 - 数据库初始化脚本
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS employee_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE employee_db;

-- =============================================
-- 部门表
-- =============================================
CREATE TABLE IF NOT EXISTS departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE COMMENT '部门名称',
    description VARCHAR(500) COMMENT '部门描述',
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_department_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- =============================================
-- 员工表
-- =============================================
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL COMMENT '名',
    last_name VARCHAR(50) COMMENT '姓',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    position VARCHAR(100) COMMENT '职位',
    salary DECIMAL(12, 2) COMMENT '薪资',
    hire_date DATE COMMENT '入职日期',
    department_id BIGINT COMMENT '所属部门ID',
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_date DATETIME COMMENT '最后更新时间',
    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id) REFERENCES departments(id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_employee_name (first_name, last_name),
    INDEX idx_employee_department (department_id),
    INDEX idx_employee_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='员工表';

-- =============================================
-- 示例数据
-- =============================================

-- 插入部门
INSERT INTO departments (name, description) VALUES
('技术部', '负责公司产品的技术研发、系统架构设计与维护'),
('市场部', '负责市场调研、品牌推广、营销策略制定与执行'),
('人力资源部', '负责招聘、培训、薪酬福利与员工关系管理'),
('财务部', '负责公司财务核算、预算管理与成本控制'),
('运营部', '负责日常运营管理、客户服务与流程优化');

-- 插入员工
INSERT INTO employees (first_name, last_name, email, phone, position, salary, hire_date, department_id) VALUES
('张', '三', 'zhangsan@company.com', '13800138001', '高级Java开发工程师', 25000.00, '2024-01-15', 1),
('李', '四', 'lisi@company.com', '13800138002', '前端开发工程师', 20000.00, '2024-03-01', 1),
('王', '五', 'wangwu@company.com', '13800138003', '技术经理', 35000.00, '2023-06-01', 1),
('赵', '六', 'zhaoliu@company.com', '13800138004', '系统架构师', 40000.00, '2022-09-01', 1),
('陈', '静', 'chenjing@company.com', '13800138005', '测试工程师', 18000.00, '2024-05-20', 1),
('刘', '波', 'liubo@company.com', '13800138006', '市场总监', 30000.00, '2023-02-15', 2),
('周', '涛', 'zhoutao@company.com', '13800138007', '市场专员', 15000.00, '2024-07-01', 2),
('吴', '芳', 'wufang@company.com', '13800138008', '品牌经理', 22000.00, '2023-08-15', 2),
('孙', '伟', 'sunwei@company.com', '13800138009', 'HR经理', 28000.00, '2022-11-01', 3),
('郑', '丽', 'zhengli@company.com', '13800138010', '招聘专员', 14000.00, '2024-04-10', 3),
('黄', '明', 'huangming@company.com', '13800138011', '财务总监', 38000.00, '2021-05-20', 4),
('林', '燕', 'linyan@company.com', '13800138012', '会计', 16000.00, '2024-02-01', 4),
('马', '超', 'machao@company.com', '13800138013', '运营经理', 26000.00, '2023-04-10', 5),
('朱', '洁', 'zhujie@company.com', '13800138014', '客服主管', 17000.00, '2024-08-15', 5);
