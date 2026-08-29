package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Long>{
}
