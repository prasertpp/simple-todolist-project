package com.demo.todos.repository;


import com.demo.todos.model.entity.TodoListTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoTransactionRepository extends JpaRepository<TodoListTransaction,String> {



}
