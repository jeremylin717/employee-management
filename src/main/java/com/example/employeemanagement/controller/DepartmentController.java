package com.example.employeemanagement.controller;

import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * 部门控制器
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 部门列表页
     */
    @GetMapping
    public String list(Model model, @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            model.addAttribute("departments", departmentService.search(keyword.trim()));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("departments", departmentService.findAll());
        }
        model.addAttribute("pageTitle", "部门管理");
        return "departments/list";
    }

    /**
     * 部门新增表单页
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("department", new Department());
        model.addAttribute("pageTitle", "新增部门");
        model.addAttribute("isEdit", false);
        return "departments/form";
    }

    /**
     * 部门编辑表单页
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentService.findById(id);
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "部门不存在");
            return "redirect:/departments";
        }
        model.addAttribute("department", departmentOpt.get());
        model.addAttribute("pageTitle", "编辑部门");
        model.addAttribute("isEdit", true);
        return "departments/form";
    }

    /**
     * 提交部门表单（新增/编辑）
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Department department,
                       BindingResult bindingResult,
                       @RequestParam(required = false) Long id,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        // 检查部门名称重复
        Optional<Department> existing = departmentService.findByName(department.getName());
        boolean isDuplicate = existing.isPresent() && !existing.get().getId().equals(id);

        if (isDuplicate) {
            bindingResult.rejectValue("name", "duplicate", "部门名称已存在");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", id != null ? "编辑部门" : "新增部门");
            model.addAttribute("isEdit", id != null);
            return "departments/form";
        }

        if (id != null) {
            // 更新
            Department existingDept = departmentService.findById(id).orElseThrow();
            existingDept.setName(department.getName());
            existingDept.setDescription(department.getDescription());
            departmentService.save(existingDept);
            redirectAttributes.addFlashAttribute("success", "部门更新成功");
        } else {
            // 新增
            departmentService.save(department);
            redirectAttributes.addFlashAttribute("success", "部门新增成功");
        }

        return "redirect:/departments";
    }

    /**
     * 删除部门
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            departmentService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "部门已删除");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败: " + e.getMessage());
        }
        return "redirect:/departments";
    }

    /**
     * 部门详情页
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Department> departmentOpt = departmentService.findById(id);
        if (departmentOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "部门不存在");
            return "redirect:/departments";
        }
        model.addAttribute("department", departmentOpt.get());
        model.addAttribute("pageTitle", "部门详情");
        return "departments/detail";
    }
}
