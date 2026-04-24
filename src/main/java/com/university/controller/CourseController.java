package com.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/courses")
    public String showCourses(HttpSession session, Model model) {
        if (session.getAttribute("studentId") == null) {
            return "redirect:/login";
        }

        String sql = "SELECT * FROM courses";
        List<Map<String, Object>> courses = jdbcTemplate.queryForList(sql);
        
        model.addAttribute("courses", courses);
        model.addAttribute("studentName", session.getAttribute("studentName"));
        
        return "courses";
    }

    @PostMapping("/register/{courseId}")
    public String registerForCourse(@PathVariable("courseId") int courseId, HttpSession session, Model model) {
        Integer studentId = (Integer) session.getAttribute("studentId");
        
        if (studentId == null) {
            return "redirect:/login";
        }

        try {
            String checkSql = "SELECT count(*) FROM registrations WHERE student_id = ? AND course_id = ?";
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, studentId, courseId);
            
            if (count != null && count > 0) {
                model.addAttribute("error", "You are already registered for this course.");
                return showCourses(session, model);
            }

            String insertSql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, studentId, courseId, LocalDate.now());
            
            System.out.println("SUCCESS: Student " + studentId + " registered for Course " + courseId);
            
            return "redirect:/success";

        } catch (Exception e) {
            System.out.println("ERROR: Failed to register student " + studentId + " for Course " + courseId);
            model.addAttribute("error", "An error occurred during registration.");
            return showCourses(session, model);
        }
    }
    
    @GetMapping("/success")
    public String showSuccess(HttpSession session) {
        if (session.getAttribute("studentId") == null) {
            return "redirect:/login";
        }
        return "success";
    }
}