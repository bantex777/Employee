package com.example.shop.dto;

import com.example.shop.model.Employee;

public class EmployeeReponse {
    
    private Long id;

    private String name;

    private String email;

    private String position;

    private Long departmentId;

    private String departmentName;

    public EmployeeReponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.position = employee.getPosition();

        if (employee.getDepartment() != null) {
            this.departmentId = employee.getDepartment().getId();
            this.departmentName = employee.getDepartment().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    
}
