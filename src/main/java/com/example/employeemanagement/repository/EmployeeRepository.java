package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 员工数据访问层
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * 按部门ID查找员工
     */
    List<Employee> findByDepartmentId(Long departmentId);

    /**
     * 按部门ID分页查找员工
     */
    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    /**
     * 按姓名模糊搜索（分页）
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Employee> searchByName(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 按职位查找员工
     */
    List<Employee> findByPositionContainingIgnoreCase(String position);

    /**
     * 按部门和姓名复合搜索
     */
    @Query("SELECT e FROM Employee e WHERE " +
           "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
           "(:keyword IS NULL OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Employee> search(@Param("departmentId") Long departmentId,
                          @Param("keyword") String keyword,
                          Pageable pageable);
}
