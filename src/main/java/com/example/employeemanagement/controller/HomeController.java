package com.example.employeemanagement.controller;

import com.example.employeemanagement.service.DepartmentService;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 */
@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalEmployees", employeeService.count());
        model.addAttribute("totalDepartments", departmentService.count());
        model.addAttribute("pageTitle", "首页");
        return "index";
    }
}
