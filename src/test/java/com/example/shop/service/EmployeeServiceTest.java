package com.example.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.shop.dto.EmployeeReponse;
import com.example.shop.dto.EmployeeRequest;
import com.example.shop.exception.EmployeeNotFoundException;
import com.example.shop.model.Department;
import com.example.shop.model.Employee;
import com.example.shop.repository.DepartmentRepository;
import com.example.shop.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void shouldCreateEmployee() {
        Department department = new Department("IT");

        ReflectionTestUtils.setField(department, "id", 1L);

        EmployeeRequest employeeRequest = new EmployeeRequest();

        employeeRequest.setName("John Carlo");
        employeeRequest.setEmail("john@example.com");
        employeeRequest.setPosition("Android Developer");
        employeeRequest.setDepartmentId(1L);

        Employee employee = new Employee();

        employee.setName("John Carlo");
        employee.setEmail("john@example.com");
        employee.setPosition("Android Developer");
        employee.setDepartment(department);

        when(departmentRepository.findById(1L))
            .thenReturn(Optional.of(department));
            
        when(employeeRepository.save(any(Employee.class)))
            .thenReturn(employee);
            
    // ACT
    
        EmployeeReponse reponse = employeeService.createEmployee(employeeRequest);

    // ASSERT

        assertNotNull(reponse);

        assertEquals("John Carlo", reponse.getName());

        assertEquals("Android Developer", reponse.getPosition());

        assertEquals("john@example.com", reponse.getEmail());

        verify(departmentRepository)
            .findById(1L);

        verify(employeeRepository)
            .save(any(Employee.class));

    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(99L));    
    }

    @Test
    void shouldThrowExceptionWhenDepartmentNotFound() {

        EmployeeRequest request = 
                            new EmployeeRequest();

        
        request.setName("John Carlo");
        request.setEmail("john@example.com");
        request.setPosition("Android Developer");
        request.setDepartmentId(99L);

        when(departmentRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.createEmployee(request));            
    }
}
