package com.example.shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.EmployeeReponse;
import com.example.shop.dto.EmployeeRequest;
import com.example.shop.model.Employee;
import com.example.shop.service.EmployeeService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/employees")
public class EmployerController {

    private final EmployeeService employeeService;

    public EmployerController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeReponse>> getEmployess() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeReponse> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeReponse> saveNewEmployee(@Valid @RequestBody EmployeeRequest entity) {
       EmployeeReponse reponse = employeeService.createEmployee(entity);

       return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeReponse> putMethodName(@PathVariable Long id, @RequestBody EmployeeRequest entity) {

        return ResponseEntity.ok(employeeService.updatEmployee(id, entity));
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
         employeeService.deleteEmployee(id);
         return ResponseEntity.noContent().build();
    }
    

}
