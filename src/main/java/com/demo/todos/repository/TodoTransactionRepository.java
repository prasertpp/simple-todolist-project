package com.demo.todos.repository;


import com.demo.todos.model.entity.TodoListTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TodoTransactionRepository extends JpaRepository<TodoListTransaction,UUID> {

     TodoListTransaction findAllByIdAndActivated(UUID id, String activated);

     List<TodoListTransaction> findAllByActivated(String activated);


}
