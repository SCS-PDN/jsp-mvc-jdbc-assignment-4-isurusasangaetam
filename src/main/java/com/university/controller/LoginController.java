package com.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        
        String sql = "SELECT student_id, name FROM students WHERE email = ? AND password = ?";
        
        try {
            Map<String, Object> student = jdbcTemplate.queryForMap(sql, email, password);
            
            session.setAttribute("studentId", student.get("student_id"));
            session.setAttribute("studentName", student.get("name"));
            
            System.out.println("SUCCESS: User logged in -> " + email);
            
            return "redirect:/courses";
            
        } catch (Exception e) {
            System.out.println("FAILURE: Failed login attempt for -> " + email);
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }
}