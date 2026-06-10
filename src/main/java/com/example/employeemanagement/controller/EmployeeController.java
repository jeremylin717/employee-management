package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.service.DepartmentService;
import com.example.employeemanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * 员工控制器
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    // 默认每页显示数量
    private static final int PAGE_SIZE = 10;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 员工列表页（支持搜索和分页）
     */
    @GetMapping
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) Long departmentId,
                       @RequestParam(required = false) String keyword) {

        Page<Employee> employeePage = employeeService.search(departmentId,
                (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null,
                page, PAGE_SIZE);

        model.addAttribute("employees", employeePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("totalItems", employeePage.getTotalElements());
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("pageTitle", "员工管理");
        return "employees/list";
    }

    /**
     * 员工新增表单页
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("pageTitle", "新增员工");
        model.addAttribute("isEdit", false);
        return "employees/form";
    }

    /**
     * 员工编辑表单页
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "员工不存在");
            return "redirect:/employees";
        }
        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("pageTitle", "编辑员工");
        model.addAttribute("isEdit", true);
        return "employees/form";
    }

    /**
     * 提交员工表单（新增/编辑）
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Employee employee,
                       BindingResult bindingResult,
                       @RequestParam(required = false) Long id,
                       @RequestParam(required = false) Long departmentId,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("pageTitle", id != null ? "编辑员工" : "新增员工");
            model.addAttribute("isEdit", id != null);
            return "employees/form";
        }

        if (id != null) {
            // 更新
            employeeService.update(id, employee, departmentId);
            redirectAttributes.addFlashAttribute("success", "员工信息更新成功");
        } else {
            // 新增
            employeeService.saveWithDepartment(employee, departmentId);
            redirectAttributes.addFlashAttribute("success", "员工添加成功");
        }

        return "redirect:/employees";
    }

    /**
     * 删除员工
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            employeeService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "员工已删除");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
        }
        return "redirect:/employees";
    }

    /**
     * 员工详情页
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employeeOpt = employeeService.findById(id);
        if (employeeOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "员工不存在");
            return "redirect:/employees";
        }
        model.addAttribute("employee", employeeOpt.get());
        model.addAttribute("pageTitle", "员工详情");
        return "employees/detail";
    }
}
