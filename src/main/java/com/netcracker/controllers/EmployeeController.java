package com.netcracker.controllers;

import com.netcracker.dao.EmployeeDao;
import com.netcracker.dto.Employee;
import com.netcracker.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeServices employeeServices;

    @RequestMapping("/adminHomepage.html")
    public String getAdminHomepage(Model model,HttpSession session){
        if(session.getAttribute("username")!=null) {
            return "adminHomepage";
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping("/searchEmployee.html")
    public String getEmployeeSearchForm(Model model,HttpSession session){
        if(session.getAttribute("username")!=null){
            model.addAttribute("employee", new Employee());
            return "searchEmployee";
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping(value = "submitEmployeeSearchForm.html",method = RequestMethod.POST)
    public String submitEmployeeSearchForm(@ModelAttribute("employee")Employee employee,Model model,HttpSession session){
        if(session.getAttribute("username")!=null){

            List<String> listOfGrades = new ArrayList<>();
            listOfGrades.add("G1");
            listOfGrades.add("G2");
            listOfGrades.add("G3");
            listOfGrades.add("G4");
            listOfGrades.add("G5");
            listOfGrades.add("G6");
            listOfGrades.add("G7");
            model.addAttribute("grade", listOfGrades);
            List<String> gender = new ArrayList<>();
            gender.add("Male");
            gender.add("Female");
            model.addAttribute("gender", gender);

            model.addAttribute("retrievedEmployee",employeeServices.getEmployeeById(employee.getEmployeeId()));
            return "editEmployee";
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping(value = "updateEmployee.html",method = RequestMethod.POST)
    public String updateEmployee(@ModelAttribute("retrievedEmployee")Employee employee,Model model,HttpSession session){
        if(session.getAttribute("username")!=null){

            if(employeeServices.updateEmployee(employee)==1){
                return "redirect:searchEmployee.html";
            }else{
                return "redirect:searchEmployee.html";
            }
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping(value = "getEmployeeRegistrationForm.html", method = RequestMethod.POST)
    public String getEmployeeRegistrationForm(Model model,HttpSession session) {
        if(session.getAttribute("username")!=null){

            model.addAttribute("newEmployee", new Employee());
            List<String> listOfGrades = new ArrayList<>();
            listOfGrades.add("G1");
            listOfGrades.add("G2");
            listOfGrades.add("G3");
            listOfGrades.add("G4");
            listOfGrades.add("G5");
            listOfGrades.add("G6");
            listOfGrades.add("G7");
            model.addAttribute("grade", listOfGrades);
            List<String> gender = new ArrayList<>();
            gender.add("Male");
            gender.add("Female");
            model.addAttribute("gender", gender);
            return "employeeRegistration";
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping(value = "addNewEmployee.html", method = RequestMethod.POST)
    public String addNewEmployee(@ModelAttribute("newEmployee") Employee employee, Model model, HttpSession session) {
        if(session.getAttribute("username")!=null){
            String message = employeeServices.addNewEmployee(employee);
            if (message.equals("true")) {
                model.addAttribute("successMessage", "New employee successfully created");
                return "adminHomepage";
            } else {
                model.addAttribute("errorMessage", message);
                model.addAttribute("newEmployee",new Employee());
                model.addAttribute("newEmployee", new Employee());
                List<String> listOfGrades = new ArrayList<>();
                listOfGrades.add("G1");
                listOfGrades.add("G2");
                listOfGrades.add("G3");
                listOfGrades.add("G4");
                listOfGrades.add("G5");
                listOfGrades.add("G6");
                listOfGrades.add("G7");
                model.addAttribute("grade", listOfGrades);
                List<String> gender = new ArrayList<>();
                gender.add("Male");
                gender.add("Female");
                model.addAttribute("gender", gender);
                return "employeeRegistration";
            }
        }else{
            return "redirect:index.html";
        }
    }

    @RequestMapping(value = "/showAllEmployees.html",method = RequestMethod.POST)
    public String showAllEmployees(Model model,HttpSession session){
        if(session.getAttribute("username")!=null){

            List<Employee> allEmployees = employeeServices.getAllEmployees();
            model.addAttribute("allEmployees",allEmployees);
            return "allEmployees";
        }else{
            return "redirect:index.html";
        }
    }
}
