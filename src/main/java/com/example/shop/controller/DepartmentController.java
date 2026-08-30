package com.example.shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.model.Department;
import com.example.shop.repository.DepartmentRepository;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    

    private final DepartmentRepository repository;

    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Department> getDepartments() {
        return repository.findAll();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department entity) {
        return repository.save(entity);
    }


}
