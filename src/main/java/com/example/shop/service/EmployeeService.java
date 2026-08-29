package com.example.shop.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.shop.dto.EmployeeReponse;
import com.example.shop.dto.EmployeeRequest;
import com.example.shop.exception.EmployeeNotFoundException;
import com.example.shop.model.Employee;
import com.example.shop.repository.EmployeeRepository;

@Service
public class EmployeeService {
    
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public List<EmployeeReponse> getAllEmployees() {
        return repository.findAll()
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

    public EmployeeReponse createEmployee(EmployeeRequest request) {
        
        Employee employee = new Employee();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPosition(request.getPosition());

        Employee saveEmployee = repository.save(employee);

        return new EmployeeReponse(saveEmployee);
    }

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

    public void deleteEmployee(Long id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        repository.deleteById(id);
    }


}
