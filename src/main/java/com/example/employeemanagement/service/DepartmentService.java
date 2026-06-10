package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 部门业务逻辑层
 */
@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 获取所有部门
     */
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    /**
     * 按ID查找部门
     */
    @Transactional(readOnly = true)
    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    /**
     * 按名称查找部门
     */
    @Transactional(readOnly = true)
    public Optional<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    /**
     * 按名称模糊搜索
     */
    @Transactional(readOnly = true)
    public List<Department> search(String keyword) {
        return departmentRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * 保存部门（新增或更新）
     */
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    /**
     * 删除部门
     * 注意：如果部门下有员工，需要先处理员工
     */
    public void deleteById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("部门不存在: " + id));
        // 解除部门与员工的关联
        department.getEmployees().forEach(emp -> emp.setDepartment(null));
        department.getEmployees().clear();
        departmentRepository.save(department);
        departmentRepository.delete(department);
    }

    /**
     * 检查部门名称是否已存在
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    /**
     * 获取部门总数
     */
    @Transactional(readOnly = true)
    public long count() {
        return departmentRepository.count();
    }

    /**
     * 获取所有部门及对应的员工数量
     */
    @Transactional(readOnly = true)
    public List<Object[]> findAllWithEmployeeCount() {
        return departmentRepository.findAllWithEmployeeCount();
    }
}
