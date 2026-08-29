package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shop.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    

}
