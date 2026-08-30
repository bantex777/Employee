package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    
}
