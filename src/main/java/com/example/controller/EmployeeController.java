package com.example.controller;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
import com.example.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 従業員操作用のコントローラ.
 */
@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * 従業員一覧を取得し、従業員一覧画面を表示する.
     *
     * @param model リクエストスコープ
     * @return 従業員一覧画面
     */
    @GetMapping("/showList")
    public String showList(Model model) {
        List<Employee> employeeList = employeeService.showList();
        model.addAttribute("employeeList", employeeList);
        return "employee/list";
    }

    /**
     * 従業員詳細を取得し、従業員詳細画面を表示する.
     *
     * @param id    従業員ID
     * @return 従業員詳細画面
     */
    @GetMapping("/showDetail")
    public String showDetail(String id, Model model, UpdateEmployeeForm form) {
        Employee employee = employeeService.showDetail(Integer.parseInt(id));

        UpdateEmployeeForm filledForm = convertToUpdateEmployeeForm(employee);

        model.addAttribute("employee", employee);
        model.addAttribute("updateEmployeeForm", filledForm);

        return "employee/detail";
    }

    private UpdateEmployeeForm convertToUpdateEmployeeForm(Employee employee) {
        String id = String.valueOf(employee.getId());
        String salary = String.valueOf(employee.getSalary());
        String dependentsCount = String.valueOf(employee.getDependentsCount());

        UpdateEmployeeForm form = new UpdateEmployeeForm();

        form.setId(String.valueOf(employee.getId()));
        form.setName(employee.getName());
        form.setGender(employee.getGender());
        form.setHireDate(employee.getHireDate());
        form.setMailAddress(employee.getMailAddress());
        form.setZipCode(employee.getZipCode());
        form.setAddress(employee.getAddress());
        form.setTelephone(employee.getTelephone());
        form.setSalary(String.valueOf(employee.getSalary()));
        form.setCharacteristics(employee.getCharacteristics());
        form.setDependentsCount(String.valueOf(employee.getDependentsCount()));

        return form;
    }

    /**
     * 従業員の扶養人数を更新し、従業員一覧画面にリダイレクトする.
     *
     * @param form 従業員情報
     * @return 従業員一覧画面
     */
    @PostMapping("/update")
    public String update(UpdateEmployeeForm form) {
        Integer id = Integer.parseInt(form.getId());
        Integer dependentsCount = Integer.parseInt(form.getDependentsCount());
        Integer salary = Integer.parseInt(form.getSalary());

        // 扶養人数をsetし更新
        Employee employee = employeeService.showDetail(id);
        employee.setName(form.getName());
        employee.setGender(form.getGender());
        employee.setHireDate(form.getHireDate());
        employee.setMailAddress(form.getMailAddress());
        employee.setZipCode(form.getZipCode());
        employee.setAddress(form.getAddress());
        employee.setTelephone(form.getTelephone());
        employee.setSalary(salary);
        employee.setCharacteristics(form.getCharacteristics());
        employee.setDependentsCount(dependentsCount);

        employeeService.update(employee);
        return "redirect:/employee/showList";
    }
}
