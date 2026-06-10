package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 部门数据访问层
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    /**
     * 按名称查找部门
     */
    Optional<Department> findByName(String name);

    /**
     * 检查部门名称是否存在
     */
    boolean existsByName(String name);

    /**
     * 按名称模糊搜索
     */
    List<Department> findByNameContainingIgnoreCase(String keyword);

    /**
     * 获取部门及员工数量（按员工数降序排列）
     */
    @Query("SELECT d, SIZE(d.employees) as empCount FROM Department d ORDER BY empCount DESC")
    List<Object[]> findAllWithEmployeeCount();
}
