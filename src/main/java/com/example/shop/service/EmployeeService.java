package com.example.shop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.shop.dto.EmployeeReponse;
import com.example.shop.dto.EmployeeRequest;
import com.example.shop.exception.EmployeeNotFoundException;
import com.example.shop.model.Department;
import com.example.shop.model.Employee;
import com.example.shop.repository.DepartmentRepository;
import com.example.shop.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    
    private final EmployeeRepository repository;
    private final DepartmentRepository rDepartmentRepository;

    public EmployeeService(EmployeeRepository repository, DepartmentRepository rDepartmentRepository){
        this.repository = repository;
        this.rDepartmentRepository = rDepartmentRepository;
    }

    public Page<EmployeeReponse> getAllEmployees(int page, int size, String sortBy, String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size,sort);

        Page<Employee> employees = 
                    repository.findAll(pageable);

        return employees.map(EmployeeReponse::new);
    }

    public List<EmployeeReponse> getEmployeeByDepartment(String department) {
        return repository
                .findByDepartment(department)
                .stream()
                .map(EmployeeReponse::new)
                .toList();
    }

    public List<EmployeeReponse> searchEmployeeByName(String name ) {
        return repository
                    .findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(EmployeeReponse::new)
                    .toList();
    }

    public EmployeeReponse getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                            .orElseThrow(() ->
                                new EmployeeNotFoundException(id)
                            );

        return new EmployeeReponse(employee);
    }

    @Transactional
    public EmployeeReponse createEmployee(EmployeeRequest request) {

        Department department = rDepartmentRepository.findById(request.getDepartmentId())
                                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPosition(request.getPosition());

        employee.setDepartment(department);

        Employee saveEmployee = repository.save(employee);

        return new EmployeeReponse(saveEmployee);
    }

    @Transactional
    public EmployeeReponse updatEmployee(
        Long id,
        EmployeeRequest request
    ) {

        
        Employee employee = repository.findById(id)
                                    .orElseThrow(() -> 
                                        new EmployeeNotFoundException(id)
                                    );

        employee.setName(request.getName());

        employee.setEmail(request.getEmail());

        employee.setEmail(request.getPosition());

        Employee updateEmployee = repository.save(employee);



        return new EmployeeReponse(updateEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        repository.deleteById(id);
    }


}
