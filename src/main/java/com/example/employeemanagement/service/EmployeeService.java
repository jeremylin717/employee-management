package com.example.employeemanagement.service;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 员工业务逻辑层
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 分页获取所有员工
     */
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    /**
     * 按ID查找员工
     */
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * 按部门ID分页查找员工
     */
    @Transactional(readOnly = true)
    public Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable) {
        return employeeRepository.findByDepartmentId(departmentId, pageable);
    }

    /**
     * 按姓名搜索员工（分页）
     */
    @Transactional(readOnly = true)
    public Page<Employee> searchByName(String keyword, Pageable pageable) {
        return employeeRepository.searchByName(keyword, pageable);
    }

    /**
     * 复合搜索：部门 + 关键词（分页）
     */
    @Transactional(readOnly = true)
    public Page<Employee> search(Long departmentId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return employeeRepository.search(departmentId, keyword, pageable);
    }

    /**
     * 保存员工（新增或更新）
     */
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * 保存员工并关联部门
     */
    public Employee saveWithDepartment(Employee employee, Long departmentId) {
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("部门不存在: " + departmentId));
            employee.setDepartment(department);
        } else {
            employee.setDepartment(null);
        }
        return employeeRepository.save(employee);
    }

    /**
     * 更新员工
     */
    public Employee update(Long id, Employee updatedEmployee, Long departmentId) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("员工不存在: " + id));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setHireDate(updatedEmployee.getHireDate());

        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new IllegalArgumentException("部门不存在: " + departmentId));
            employee.setDepartment(department);
        } else {
            employee.setDepartment(null);
        }

        return employeeRepository.save(employee);
    }

    /**
     * 删除员工
     */
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * 获取员工总数
     */
    @Transactional(readOnly = true)
    public long count() {
        return employeeRepository.count();
    }

    /**
     * 获取部门下的员工总数
     */
    @Transactional(readOnly = true)
    public long countByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).size();
    }
}
