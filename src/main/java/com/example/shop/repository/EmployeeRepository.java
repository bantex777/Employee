package com.example.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee,Long>{

    List<Employee> findByNameContainingIgnoreCase(String name);

    @Query("""
            SELECT e
            FROM Employee e
            JOIN FETCH e.department
            """)
    List<Employee> finnAllWithDepartment();

    @Query("""
            SELECT e FROM Employee ea
                WHERE e.position = :position
            """)
    List<Employee> findByPosition(@Param("position") String position);

    @Query("""
            SELECT e FROM Employee e
            JOIN e.department d
            WHERE d.name = :departmentName
            """)
    List<Employee> findByDepartment(@Param("departmentName") String department);



}   
